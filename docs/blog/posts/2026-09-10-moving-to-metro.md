---
title: "Rekindling the spark: moving Raccoon to Metro"
date: 2026-09-10
categories:
  - dev
  - libs
tags:
  - kmp
  - di
---

## "For now" was doing a lot of heavy lifting

The [last article](2026-08-11-koin-is-back.md) ended with the words "you can consider this the end,
for now, of my concerns about DI."

To be honest, when I wrote that sentence, I was far from happy with the state of things. That was
precisely why I cautiously tacked on that parenthetical _"for now"_.

## The cracks in KCP

Between mid-July and early August, I had, in chronological order:

- Migrated Raccoon from Kodein back to Koin out of genuine concern for the future of KOSI and the
  libraries they maintain;
- Written a convention plugin to factor out common DI configuration across subprojects (adapting
  50+ modules in the previous step made me want to make future work easier on myself);
- Evolved from the "classic DSL" style to the Koin Compiler Plugin (KCP) with annotations, aiming to
  reduce manual wiring to a minimum and gain compile-time validation.

This was a substantial amount of work — with *not a single* visible benefit for end-users — and it
forced me to freeze all new feature development and enter "maintenance mode," sticking strictly to
bug fixes, dependency updates, and code cleanup.

What made it worse was that I wasn't even 100% satisfied with the end result. These were the issues
that nagged at me most:

- **Kotlin 2.4.10 incompatibility:** KCP wasn't fully compatible with the most recent version of
  Kotlin, emitting warnings for every module on every build;
- **iOS false positives:** compile-time validation triggered false positives on iOS that broke iOS
  builds entirely, forcing me to disable it (and configuring that conditionally based on build
  target inside a Gradle convention plugin was anything but straightforward);
- **False negatives and runtime crashes:** I realized validation was also yielding false
  negatives, meaning I could have a perfectly compiling project that still crashed at runtime due to
  migration oversights, e.g. marking a parameter with `@InjectedParam` (in either a factory
  function or a constructor) and then forgetting to pass it at the call site.

## Rekindling the spark: Why Metro?

In 2026, there **had to be** something better in the KMP ecosystem. That's when a tiny voice in my
head whispered: "Have you considered switching to Metro?"

I've been doing Android development for longer than I'm comfortable admitting (though my silver hair
and beard betray me). I'm quite well-versed in Dagger/Hilt on native Android, and I'd been following
the Metro project with keen interest—mixed with equal parts hope and apprehension.

I had spent months reading Zac Sweers' documentation, articles, and posts on the `kotlinlang.org`
Slack, listening to all his talks. I'd been dreaming that one day I could convert Raccoon, while
simultaneously worrying about how long and painful such a migration might be.

Last weekend, I started a playground project to test out the framework, evaluate its setup
complexity and reproduce the specific edge cases I knew Raccoon presented. Then the work week hit,
and it was a tough one. I had to churn through a series of difficult and remarkably unrewarding
tasks.

After a couple of days of grinding, I decided I needed to compensate by building something that
rekindled that "little spark": the feeling every developer gets when they finish a piece of work and
are genuinely satisfied with the outcome.

So, one evening, I took the plunge and started the Metro migration.

## Rolling up my sleeves: the migration plan

The following section isn't meant to be a general tutorial on how to use Metro: the project already
has excellent documentation, especially if you're already familiar with Dagger/Hilt,
kotlin-inject, or Anvil.

Instead, this is a chronicle of my personal approach to migrating Raccoon's specific codebase.

Given its multi-module architecture, I mapped out a six-step plan:

1. Adapt the build logic and plugin setup;
2. Define the root graph and entry points;
3. Migrate core infrastructure;
4. Migrate the business logic;
5. Migrate all feature modules;
6. Final cleanup.

### Step 1: Build logic and plugin setup

This step involved creating a Gradle convention plugin to factor out the common DI configuration
across all subprojects. The convention plugin applies the `dev.zacsweers.metro` plugin (version
`1.4.3`) and provides an extension option to also add the
`dev.zacsweers.metro:metrox-viewmodel-compose` dependency when needed.[^1]

### Step 2: Defining the root graph and entry points

Next came defining the `RootGraph` interface in the `:shared` subproject, exposing essential
accessors like `UiDeps` (crucial for UI-related dependencies) and `AuthManager` (required for the
OAuth2 login flow):

```kotlin
interface RootGraph : ViewModelGraph {
    val authManager: AuthManager
    val uiDeps: UiDeps
}
```

Note that this interface is **not** annotated with `@DependencyGraph`, because the actual concrete
graphs are platform-specific:

```kotlin
// in :androidApp
@DependencyGraph(scope = AppScope::class)
interface AndroidRootGraph :
    RootGraph,
    MetroAppComponentProviders,
    // exposes deps to the PushService subclass
    PushNotificationComponent,
    // exposes deps for the pull notification Worker
    PullNotificationComponent {

    @Provides
    fun provideContext(): Context = MainApplication.instance
}

// in :desktopApp
@DependencyGraph(scope = AppScope::class)
interface DesktopRootGraph : RootGraph

// in the iosMain source set in :shared
@DependencyGraph(scope = AppScope::class)
interface IosRootGraph : RootGraph
```

The main `@Composable` entry point of the application was adapted to accept `RootGraph` as a
parameter.

On the platform sides:

- **Android:** `MainApplication` was updated to invoke the `createGraph` factory inside
  `onCreate()`, while `MainActivity` was updated to retrieve the root graph reference from the
  `Application` instance, use it for the OAuth2 login flow, and pass it down into the Composable
  tree.
- **JVM (desktop):** The `main()` function was updated to call the `createGraph` factory and pass
  the resulting graph down into Composition.
- **iOS:** `MainViewController` was updated to lazily call the `createGraph` factory (passing it
  into Composition), and `iOSApp` was adjusted to access `AuthManager` via
  `MainViewControllerKt.iosRootGraph` for OAuth2 authentication.

### Step 3: Migrating core infrastructure

In this phase, all subprojects under the `:core` group were migrated, focusing primarily on remote
API services, local persistence, and native utilities. While I won't detail every single class, a
couple of key highlights illustrate the flavor of the migration.

Remote API services relied heavily on assisted injection. The centralized factory, which previously
relied on Koin's dynamic runtime parameter resolution based on class types, was refactored to use
explicit pattern matching over types.

Before:

```kotlin
@Factory
internal class DefaultAppService(@InjectedParam args: ServiceCreationArgs) : AppService {
    /* ... */
}

@Single
internal class DefaultServiceFactory : ServiceFactory, KoinComponent {
    override fun <T : Any> create(clazz: KClass<T>, args: ServiceCreationArgs): T =
        getKoin().get(clazz = clazz, qualifier = null, parameters = { parametersOf(args) })
}
```

After:

```kotlin
@AssistedInject
class DefaultAppService(@Assisted args: ServiceCreationArgs) : AppService {
    /* ... */
}

@AssistedFactory
fun interface AppServiceFactory {
    fun create(@Assisted args: ServiceCreationArgs): DefaultAppService
}

@ContributesBinding(AppScope::class)
@Inject
class DefaultServiceFactory(
    private val appServiceFactory: AppServiceFactory,
    /* ... other factories... */
) : ServiceFactory {

    override fun <T : Any> create(clazz: KClass<T>, args: ServiceCreationArgs): T {
        val service = when (clazz) {
            AppService::class -> appServiceFactory.create(args)
            /* ... remaining `when` branches with other service types... */
            else -> throw IllegalArgumentException("Unknown service class: ${clazz.simpleName}")
        }
        return clazz.cast(service)
    }
}
```

I intentionally refrained from migrating `@Single` to `@SingleIn(AppScope::class)` here because
doing so would be redundant: `ServiceFactory` is injected only once in the entire project inside
`ServiceProvider` (which itself is a singleton). Furthermore, even if it were injected elsewhere,
leaving it unscoped offers distinct advantages:

- It is a lightweight, stateless object, so instantiation overhead is negligible;
- It holds no internal state, eliminating concurrency or interference risks;
- Unscoped instances can be garbage collected as soon as they go out of scope, rather than lingering
  permanently in memory.

!!! Tip

    As a general rule during this migration, leaving dependencies *unscoped* was always the
    preferred default whenever possible.

Here is the exact decision heuristic I used to decide whether to scope a dependency or keep it
unscoped:

```
Is the class a ViewModel?
  ├── YES ──> ✅ Use @Inject + @ContributesIntoMap(AppScope::class) with @ViewModelKey
  └── NO ──> Does it hold in-memory state or flows shared across components?
              ├── YES ──> ✅ USE @Inject + @SingleIn(AppScope::class)
              └── NO ──> Is it heavy or expensive to construct?
                          ├── YES ──> ✅ USE @Inject + @SingleIn(AppScope::class)
                          └── NO ──> ❌ DON'T scope ✅ USE unscoped (just @Inject)
```

### Step 4: Migrating the business logic

This step involved migrating all subprojects in the `:domain` group, which went smoothly without
major surprises. The key detail was preserving the singleton scope (`@SingleIn(AppScope::class)`)
for swipe navigation state while keeping newly provided pagination instances unscoped.

In Raccoon, the domain layer also handles push and pull background notifications on Android. As
mentioned in [Step 2](#step-2-defining-the-root-graph-and-entry-points), the dependencies needed
inside our background Worker were previously retrieved via Koin and are now exposed through
`PullNotificationComponent`.

Before:

```kotlin
@KoinWorker
internal class CheckNotificationWorker(
    context: Context,
    parameters: WorkerParameters,
    // injected by the framework
    private val inboxManager: InboxManager,
    private val strings: Strings,
) : CoroutineWorker(context, parameters) {
    /* ... */
}
```

After:

```kotlin
internal class CheckNotificationWorker(
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    private val component: PullNotificationComponent
        get() =
            (applicationContext as? MetroApplication)?.appComponentProviders as? PullNotificationComponent
                ?: error("PullNotificationComponent not found")

    private val inboxManager: InboxManager by lazy { component.inboxManager }

    private val strings: Strings by lazy { component.strings }

    /* ... */
}
```

### Step 5: Migrating all feature modules

This group of modules houses UI and presentation logic. For the UI layer, the `UiDeps`
accessor (providing access to UI helpers like `NavigationCoordinator`, `MainRouter`,
`DrawerCoordinator`, `ClipboardHelper`, etc.) introduced
in [Step 2](#step-2-defining-the-root-graph-and-entry-points) finally came into action.

In the old setup, many of these dependencies were fetched directly inside navigation containers
using `koinInject()`. That pattern is typical of service locators, but in anticipation of the Metro
migration, I had already minimized those calls. Now, after retrieving `UiDeps` from `RootGraph`, it
is passed down through child composables via a `CompositionLocal`.[^2]

As for presentation logic, `ViewModel`s are the star of the show, and the `metrox-viewmodel-compose`
artifact made migrating them surprisingly straightforward.

Many `ViewModel`s take assisted constructor parameters (e.g., navigation arguments). We already saw
how `@AssistedInject` works in [Step 3](#step-3-migrating-core-infrastructure); the only new
requirement here was subclassing `ViewModelAssistedFactory` and annotating it with
`@ViewModelAssistedFactoryKey`:

```kotlin
@AssistedInject
class LoginViewModel(
    @Assisted args: LoginViewModelArgs,
    /* ... all non-assisted injected params... */
) : ViewModel() {
    /* ... */

    companion object {
        val KEY_ARGS = CreationExtras.Key<LoginViewModelArgs>()
    }
}

@Serializable
data class LoginViewModelArgs(val type: LoginType)

@AssistedFactory
@ViewModelAssistedFactoryKey(LoginViewModel::class)
@ContributesIntoMap(AppScope::class)
interface LoginViewModelFactory : ViewModelAssistedFactory {
    override fun create(extras: CreationExtras): LoginViewModel =
        create(extras[KEY_ARGS] ?: error("ViewModel args not found"))

    fun create(@Assisted args: LoginViewModelArgs): LoginViewModel
}
```

At the call site, `assistedMetroViewModel()` serves as a near drop-in replacement for
`koinViewModel()`, with arguments passed through `CreationExtras`:

```kotlin
val model = assistedMetroViewModel<LoginViewModel>(
    extras = CreationExtras {
        this[KEY_ARGS] = LoginViewModelArgs(loginType.toLoginType())
    },
)
```

The non-assisted case, on the other hand, is even simpler:[^3]

```kotlin
@ContributesIntoMap(AppScope::class)
@ViewModelKey
@Inject
@OptIn(FlowPreview::class)
class TimelineViewModel(
    /* ... injected params... */
) : ViewModel() {
    /* ... */
}
```

Where at the composable call site, it boils down to:

```kotlin
val model = metroViewModel<TimelineViewModel>()
```

### Step 6: Final cleanup

This was by far the most satisfying part: stripping out all Koin references, cleaning up the version
catalog, and deleting the DI setup helper object where all Koin `@Module`s were previously
registered by hand.

## The battle scars: Top technical blockers

Unsurprisingly, when migrating a medium-sized project with 50+ modules and 150+ injected
dependencies, not everything worked perfectly on the first attempt. Here is a breakdown of the
mistakes I made along the way—along with their symptoms, root causes, and fixes.

### 1. Multi-module Gradle dependency scope

- **Symptom:** Compile-time error (`[Metro/MissingBinding]`) or, worse, runtime
  `IllegalArgumentException` when building `:androidApp`.
- **Cause:** `:shared` included feature modules using `implementation(projects.feature.timeline)`.
  In Gradle, `implementation` isolates transitive dependencies. Metro's `@DependencyGraph` processor
  running in `:androidApp` couldn't discover `@ContributesBinding`/`@ContributesIntoMap` in feature
  modules because they were missing from `:androidApp`'s compilation classpath.
- **Solution:** Replace `implementation` with `api` in `shared/build.gradle.kts` for all feature and
  domain modules.

### 2. Visibility modifiers and cross-module bindings

- **Symptom:** Compile-time error (`[Metro/MissingBinding]`).
- **Cause:** Classes annotated with `@ContributesBinding(AppScope::class)` were declared as
  `internal`. Metro generates `internal` bindings that the root graph in platform-specific modules (
  `:androidApp` or `:desktopApp`) cannot access across Gradle module boundaries.
- **Solution:** Remove the `internal` visibility modifier from `@ContributesBinding`-annotated
  classes.

### 3. `ViewModel` multi-bindings with multiple supertypes

- **Symptom:** Compile error:
  `@ContributesIntoMap-annotated class doesn't declare an explicit binding type but has multiple supertypes`.
- **Cause:** `ViewModel`s that extended `ViewModel()` and also implemented MVI contract interfaces
  had multiple supertypes, creating ambiguity for Metro's implicit supertype resolution.
- **Solution:** Explicitly pass the `binding` parameter and use `@ViewModelKey` inside the
  generic type argument e.g. `binding<@ViewModelKey ViewModel>()` (leaving the interred class key).

### 4. Swift / KMP Framework export boundaries

Last but not least, a `KotlinNativeTarget` configuration issue!

- **Symptom:** Swift compilation error: `cannot find 'X' in scope` or
  `type 'MainViewControllerKt' has no member X`.
- **Cause:** Swift only exposes types from packages explicitly listed in the framework's export
  declaration. Top-level functions in non-exported files or Kotlin `object`s outside exported
  packages are omitted from the generated Swift header.
- **Solution:** Ensure the KMP framework is configured with the correct `export(...)` directives and
  that `:shared` exposes its transitive dependencies via `api` (
  see [Blocker 1](#1-multi-module-gradle-dependency-scope)).

[^1]: The `dev.zacsweers.metro:metrox-android` dependency was added manually to the `:androidApp`
module.

[^2]: This was also a great opportunity to refactor our `CompositionLocal` providers and streamline
the "startup ceremony" required for UI components (making UI tests and Compose previews much
cleaner).

[^3]: In my case, it was slightly more involved because in my MVI setup all `ViewModel`s also
implement another interface, and the UI refers to them through that interface type.
See [Blocker 3](#3-viewmodel-multi-bindings-with-multiple-supertypes) for a more detailed
explanation.

*[DSL]: Domain-Specific Language
*[DI]: Dependency Injection
*[KOSI]: Kodein Open Source Initiative
*[KCP]: Koin Compiler Plugin
*[KMP]: Kotlin Multiplatform
*[API]: Application Programming Interface
*[UI]: User Interface
*[MVI]: Model-View-Intent
