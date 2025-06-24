---
layout: post
title: "Koin VS Kodein: a developer's journey through multiplatform DI hell"
date: 2025-06-27
tags: [ "kmp" ]
---

Dependency injection (or DI) has been the backbone of Android development for years, bringing
flexibility, easier refactoring, and proper lifecycle management through decoupling and abstraction.
But when you venture into Kotlin Multiplatform (KMP) territory, the comfortable world
of [Dagger](https://dagger.dev/) and [Hilt](https://dagger.dev/hilt/) suddenly becomes unavailable.

This is the story of my two-year journey building Raccoon for Lemmy and Raccoon for Friendica, and
how I learned the hard way that not all DI solutions are created equal.

## The multiplatform DI dilemma

### Why Dagger and Hilt don't work

The gold standards of Android DI face fundamental barriers in KMP:

- **Java dependency**: noth rely heavily on generated Java code, making them incompatible with
  native platforms like iOS;
- **KAPT legacy**: until late 2023 (versions < 2.49), Dagger was tightly coupled with KAPT
  (Kotlin Annotation Processing Tool), while KMP uses **KSP** (Kotlin Symbol Processor).

### Enter the alternatives

With traditional solutions off the table, the KMP community turned to
alternatives. [Koin](https://insert-koin.io/) emerged as a popular choice, with its lead developer
Arnaud Giuliani making bold claims about multiplatform compatibility since 2021.

But here's the thing: **Koin isn't actually a proper DI framework — it's a service locator**. This
distinction matters more than you might think. As a matter of fact, a lot of experienced Android
developers do not consider Koin worth to be used large-scaled industry-level projects (as a rule of
thumb, the greater the seniority the more "toy tools" are frowned upon). But why trusting blindly
other people's prejudices, when you can try things directly?

## Round 1: Koin without annotations

### The setup

When I started Raccoon for Lemmy in 2023, Koin seemed like the logical choice:

- Strong multiplatform support claims;
- Integration with Voyager navigation library;
- Simplified `ViewModel` injection and lifecycle management.

I went with the manual module definition approach, fully aware of the risks that
`NoBeanDefFoundException` was just around the corner if I misconfigured the DI or forgot to define a
binding.

### The Reality Check

**The good:**

- Project completed successfully;
- Apps published on Google Play and F-Droid;
- Complex interface-to-implementation binding in platform-specific source sets worked.

Concerning the last point, I was also able to handle complex scenarios where you have an interface
in the `commonMain` source set and you want to bind it to different implementations in
platform-specific source sets.

The idea was to simply have the native module as an `expect val` and several `actual` modules with
native bindings (see example [below](#platform-specific-bindings)).

**The bad:**

- No compile-time validation: `NoBeanDefFoundException` lurking around every corner;
- A lot of boilerplate code to define modules manually.

The lack of compile-time safety became increasingly problematic as the project grew. Unlike Dagger,
where DI errors prevent compilation, Koin happily lets you ship broken apps if you forget a binding
or including some module (it happened, for
example [here](https://github.com/LiveFastEatTrashRaccoon/RaccoonForLemmy/commit/020f322fa6345e9df2874c11a860d357169c9f70)).

## Round 2: Koin-Annotations migration

### The promise

When starting Raccoon for Friendica, I decided to challenge myself with Koin-Annotations, lured by
promises of:

- Compile-time validation;
- Reduced boilerplate;
- Industry-ready reliability.

### The implementation nightmare

#### KSP configuration hell

Setting up KSP in a multi-module KMP project was brutal:

- Documentation was sparse (mid-2024)
- Trial and error for days
- Multiple modules made everything trickier, but I eventually created
  a Gradle [convention plugin](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/blob/d4e3c7327b0c21355b372f9e1fdb117425fce94a/build-logic/convention/src/main/kotlin/plugins/KoinWithKspPlugin.kt)
  to properly configure all subprojects.

#### Platform-specific bindings

The elegant solution for platform-specific implementations became a little more convoluted:

**Before (manual):**

```kotlin
// in commonMain source set
interface SomeInterface {
    fun someFunction()
}

expect val nativeModule: Module

// in platform-specific source sets
class SomeImplementation : SomeInterface {
    override fun someFunction() = Unit
}

actual val nativeModule = module {
    single<SomeInterface> {
        SomeImplementation()
    }
}
```

**After (Annotations):**

```kotlin
// in commonMain source set
interface SomeInterface {
    fun someFunction()
}

@Single
expect class SomeImplementation : SomeInterface {
    override fun someFunction()
}

@Module
expect class SomeModule

// in platform-specific source sets
@Single
actual class SomeImplementation : SomeInterface {
    override fun someFunction() = Unit
}

@Module
@ComponentScan
actual class SomeModule
```

This required the experimental `-Xexpected-actual-classes` compiler argument and significantly more
boilerplate (notice the repeated `@Single` scopes, the empty method body in the expect class, etc.).

Nonetheless, I rolled up my sleeves and embarked in the journey:

- **~200 DI bindings per app**
- **Multiple Gradle subprojects**
- **Several weeks of intensive work (in my spare time)**

### The F-Droid catastrophe

Just when I thought the migration was successful, disaster struck. The Lemmy app, which had been
successfully building on F-Droid, suddenly failed their reproducible build requirements.

#### The root cause

Koin-Annotations generates metadata classes with **time-based hashes** that change on every
compilation. This breaks reproducible builds — a critical requirement for F-Droid's security policies.

#### The maintainer response

I opened [an issue](https://github.com/InsertKoinIO/koin-annotations/issues/200) explaining the
problem. The response was disappointing:

- No acknowledgment of the issue;
- [Similar issues](https://github.com/InsertKoinIO/koin-annotations/pull/276) remain unaddressed
  after 6+ months;
- Arrogant dismissal (to the other developer reporting the same issue I encountered): lead
  maintainer doesn't understand why this is an issue
  (see [here](https://github.com/InsertKoinIO/koin-annotations/pull/276#issuecomment-2959925386)).

For an open-source project where F-Droid represented my main user base, this was unacceptable.

## Round 3: Kodein to the rescue

### Why Kodein?

Frustrated with Koin's maintainership and technical issues, I looked for alternatives.
[Kodein](https://kosi-libs.org/kodein/7.25/index.html) caught my attention:

- **"Painless dependency injection"** tagline (painkillers were exactly what I needed);
- Integration with Voyager navigation;
- No pretentious marketing claims: it was honest about what it is and isn't.

### The migration experience

#### What I expected

Another painful multi-week migration process.

#### What I got

- **Clear, comprehensive documentation**
- **Practical examples**
- **Straightforward integration**
- **Excellent Compose multiplatform support**

#### Technical considerations

The only "tricky" part was Android `Context` binding (Koin had dedicated constructs for this):

```kotlin
// in commonMain source set
fun initDi(additionalBuilder: DI.Builder.() -> Unit = {}) {
    RootDI.di =
        DI {
            additionalBuilder()
            // rest of module imports and/or definitions
        }
}

// in androidMain source set
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDi {
            bind<Context> { provider { applicationContext } }
        }
    }
}
```

whereas in the `iOSApp.swift`:

```swift
@main
struct iOSApp: App {
    init() {
        DiHelperKt.doInitDi { _ in }
    }
}
```

### The results

- **Seamless cross-platform portability**
- **No build reproducibility issues**
- **Cleaner architecture** (thanks to accumulated experience)
- **Reliable, predictable behavior**

## Key lessons learned

### 1. Tool selection matters

For cross-functional infrastructure (DI, navigation, logging), choose tools based on:

- **Maintenance quality**: how are issues handled?
- **Community engagement**: are contributions properly managed?
- **Documentation**: is it clear and comprehensive?
- **Stability vs. innovation balance**: new features shouldn't compromise reliability

### 2. Design for flexibility

- Keep DI structure modular and replaceable
- Abstract away framework-specific details
- Design interfaces that don't leak implementation concerns
- Changes are inevitable — embrace them as improvement opportunities

### 3. Stay open to innovation

The KMP DI landscape continues evolving. Keep an eye on emerging solutions like:

- **[Metro](https://zacsweers.github.io/metro/)**: a promising new KMP DI tool
- Future Dagger/Hilt KMP support
- Other community-driven alternatives

## The final veredict: a comparison

| Aspect                  | Koin (Manual) | Koin-Annotations | Kodein |
|-------------------------|---------------|------------------|--------|
| **Compile-time Safety** | ❌             | ✅                | ❌      |
| **Setup Complexity**    | ⭐⭐            | ⭐⭐⭐⭐⭐            | ⭐⭐     |
| **Documentation**       | ⭐⭐⭐           | ⭐⭐               | ⭐⭐⭐⭐⭐  |
| **Maintenance**         | ⭐⭐            | ⭐                | ⭐⭐⭐⭐   |
| **F-Droid Compatible**  | ✅             | ❌                | ✅      |
| **Learning Curve**      | ⭐⭐            | ⭐⭐⭐⭐             | ⭐⭐     |

## Final thoughts

Sometimes the best solution isn't the most popular or heavily marketed one. Kodein's honest,
straightforward approach to dependency injection proved more valuable than Koin's flashy promises
and problematic execution.

The journey taught me that in software development, as in life, **reliability trumps hype every
time**. When building production applications that need to work across multiple platforms and
distribution channels, choose tools that do what they say they will do — nothing more, nothing less.

For your next KMP project, consider giving Kodein a try. Your future self (and your F-Droid users)
will thank you.

---

*Have you had similar experiences with DI libraries in KMP? Reach out to your thoughts and war
stories.*
