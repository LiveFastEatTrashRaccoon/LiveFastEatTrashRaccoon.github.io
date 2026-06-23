---
title: "One Preview to Rule Them All: Unified Compose Previews in Common Code"
date: 2026-06-24
tags:
  - compose
  - kmp
---

*Published on June 24, 2026*

Until late 2025, managing UI previews in a Compose Multiplatform (CMP) project felt like navigating
a minefield of compromises. The ecosystem was fragmented, and achieving a seamless "write once, see
everywhere" experience was surprisingly difficult.

## The Fragmented Past of CMP Previews

Back in the dark ages, there were three distinct ways to handle previews:

1. `androidx.compose.ui.tooling.preview.Preview`: the standard for `androidMain`, well-supported by
   Android Studio and IntelliJ IDEA, requiring `compose.preview` in your dependencies;
2. `androidx.compose.desktop.ui.tooling.preview.Preview`: specific to `desktopMain`, supported by
   IDEs via a plugin with minimal build setup;
3. `org.jetbrains.compose.ui.tooling.preview.Preview`: the common option that worked across all
   source sets, but with limited IDE support (mostly restricted to Fleet), requiring
   `compose.components.uiToolingPreview` in `commonMain`.

For my workflow, none of these were perfect. I never found Fleet to be a viable daily driver due to
its early-stage stability and uncertain pricing model, so option #3 was out. Since my focus was
primarily on mobile, the desktop-only preview (#2) wasn't of much help either.

That left me with the Android-specific annotation (#1). However, since this annotation was only
recognized within the `androidMain` source set and all my Composables lived in `commonMain`, I
would have been forced to maintain previews in separate files. Worse yet, I couldn't see them unless
I kept the Android counterpart open in a parallel pane.

So I decided to wait for the ecosystem to mature. After all, the friction only affected my
Developer Experience, end-users were not affected in any way.

## The Turning Point

The game changed with the release of **Compose Multiplatform 1.10.0**. This version introduced a
unified `androidx.compose.ui.tooling.preview.Preview` annotation that can be used directly in
`commonMain` and is fully recognized by both Android Studio and IntelliJ IDEA.

Of course, a unified experience requires some intentional configuration. To get this working some
steps are needed:

- add the `org.jetbrains.compose.ui:ui-tooling-preview` dependency to your `commonMain` source set;
- if you apply the `com.android.kotlin.multiplatform.library` plugin, you must ensure
  `org.jetbrains.compose.ui:ui-tooling` is available in the `androidRuntimeClasspath`.

In my project, I manage configuration through a convention plugin. I had to adjust my
`KotlinMultiplatformAndroidLibraryExtension` setup to inject the necessary runtime dependency:

```kotlin
class ComposeMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            extensions.configure(KotlinMultiplatformExtension::class.java) {
                targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java)
                    .configureEach {
                        apply<KotlinMultiplatformAndroidLibraryExtension> {
                            dependencies.add(
                                "androidRuntimeClasspath",
                                libs.findLibrary("compose-ui-tooling").get(),
                            )
                        }
                    }
            }
        }
}
```

## The Resource Roadblock

I thought I was in the clear. But as it turns out, I had only scratched the surface. As soon as I
started adding the first preview, I realized that my previous architectural decisions regarding
resource management were blocking the way.

My UI relied heavily on `CompositionLocal`s for drawables and localized strings. Furthermore,
resource access was abstracted away through interfaces (`CoreResources` and `Strings`), with
concrete implementations (`DefaultCoreResources` and `DefaultStrings`) locked inside the
`:shared` subproject.[^1]

This architecture was a relic of an era before native Compose resources, back when I used Lyricist
and moko-resources. This setup had remained incredibly flexible though, and I wasn't ready to
abandon it just for the sake of previews.

To preserve both subproject-specific previews and clean encapsulation, I performed a targeted
refactor. I moved the concrete resource adapters from `:shared` to their respective modules
(`:core:resources` and `:core:l10n`). I also exposed the DI bindings via public modules
(`resourceModule` and `l10nModule`), which were previously internal in `:shared`.

Eventually, I created an extension function in `:core:commonui:components` to factor out the DI
setup
for previews:

```kotlin
@Composable
fun RootDI.SetupPreview(vararg modules: DI.Module) = remember {
        di = DI {
            importAll(resourcesModule, l10nModule, *modules)
        }
    }
```

This refactoring didn't just give me functional previews in common code; it also allowed me to
eliminate redundant test doubles for resources. Because my unit tests now have access to the actual
resource adapters within their subprojects, the tests are both simpler and more realistic.

## Closing Thoughts

Unified previews have bridged the gap between common code and visual feedback, making the
"multiplatform" part of Compose truly first-class. In the future, I'll be adding more of them
(as of now, I concentrated on `:core:commonui:components` as I was experimenting): it makes
it easier to maintain the project and spot bugs earlier.

In my case, I believe waiting until a mature solution emerged was a winning strategy. Balancing
trade-offs between functionality and complexity is worth if it improves UX, but here it
only involved DX (and this is mostly a one-man project); so it could be put off with no impact.

Finally, as always, adopting new parts of a technology was an opportunity for me to improve code
architecture, remove obsolete workarounds and cleanup boilerplate. And this is one of the main
reasons I work on this project: learn new things, refactor, improve code quality and ultimately
myself as a developer.

[^1]: In this context "subproject" and "module" can not be used interchangeably. From now on, "
subproject" will refer to a build configuration unit, "module" will refer to a DI configuration
unit.

*[CMP]: Compose Multiplatform
*[API]: Application Programming Interface
*[DI]: Dependency Injection
*[UI]: User Interface
*[UX]: User eXperience
*[DX]: Developer eXperience
