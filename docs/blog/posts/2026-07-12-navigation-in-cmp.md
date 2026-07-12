---
title: "Navigation in CMP: To Infinity and Beyond!"
date: 2026-07-12
tags:
  - compose
  - kmp
  - libs
---

*Published on July 12, 2026*

On mobile, and increasingly on desktop, every app needs a solid navigation system. It isn't just a
UI pattern (like master-detail) or managing back gestures; it's the very skeleton of the user
experience and a fundamental block in the user's mental model. When taking certain actions, users
expect to transition from one fullscreen page to another; when taking others, they expect a modal
(dialog or bottom sheet) to appear, and so on.

## Why Navigation is Complex

Unfortunately, Navigation is rarely straightforward. In declarative UI paradigms like Compose,
components render a state that needs to be retained across recompositions. Depending on the
platform, this state must also survive **configuration changes** and **process death**.

As I discussed in a previous [article](2025-07-04-hold-my-state.md){ target=_blank }, state holders
have lifecycles bound to a view's visibility. When a view enters the viewport, the holder is born;
when it's gone, it's destroyed to free up memory.

Sometimes, parameters need to be passed back and forth between navigation entries: input data
for an item accessed from a list, or output data from a user selection. Other times, "shared data"
must be retained across several entries, such as in wizards or user journeys spanning multiple
screens. 

All of this has deep implications for DI, memory management, and app architecture.

## Choosing a Path

When I started with CMP in 2023, JetBrains hadn't yet released an official navigation solution. The
community filled this void with several alternatives:

* [Voyager](https://github.com/adrielcafe/voyager){ target=_blank }
* [Guia](https://github.com/roudikk/guia){ target=_blank }
* [Precompose](https://github.com/Tlaster/PreCompose){ target=_blank }
* [Decompose](https://github.com/arkivanov/Decompose){ target=_blank }
* [Tiamat](https://github.com/ComposeGears/Tiamat){ target=_blank }

In my early experiments, I tested Precompose and Decompose on desktop but ultimately chose
**Voyager** for mobile. Its practicality was its biggest selling point: the "Holy Trinity" of `Screen`,
`ScreenModel`, and `Navigator` made everything feel easy and compact.

## The Voyager Reality Check

To be honest: something felt off right from the start: the model was quite intrusive. In Compose,
all UI elements (including fullscreen pages) are functions, but in Voyager, screens were objects. 
Over time, as complexity piled up, cracks began to show.

### The Serialization Trap

I discovered the hard way that `Screen`s aren't regular objects that can hold any instance
variables. On Android, for example, all their members must be either primitives or types that can 
be stored in a `Bundle`.

To handle multiplatform state restoration, you have to define an `expect interface`,[^1] actualized
by a tagging interface on non-JVM platforms and by a `typealias` of `java.io.Serializable` (details 
[here](https://voyager.adriel.cafe/state-restoration/#multiplatform-state-restoration){ target=_blank }).

This was quite fragile and I remember it breaking during Gradle/AGP upgrades. 

I eventually opted to fall back on primitives (like IDs) for passing data between screens and added
a custom cache layer just to store items before a transition.

### Nested Nav and Deep Links

Even before tackling adaptive layouts, I had to deal with nested navigation (main stack + bottom
bar + drawer). I ended up encapsulating the logic into adapters and helpers (like
`NavigationCoordinator` and `NavigationAdapter`, plus a `MainRouter` to manage modularization by
feature).

Then there were deep links. Voyager's own documentation 
[admits](https://voyager.adriel.cafe/deep-links){ target=_blank } that it doesn't provide a 
built-in solution for handling deep link URIs.

## Switch to Navigation Compose

In 2024, JetBrains ported **Navigation Compose** to Multiplatform, starting with version
[1.6.10](https://blog.jetbrains.com/kotlin/2024/05/compose-multiplatform-1-6-10-ios-beta){ target=_blank }. 
By the end of that year, version 
[1.7.0](https://blog.jetbrains.com/kotlin/2024/10/compose-multiplatform-1-7-0-released){ target=_blank } 
also introduced type-safe navigation.

Therefore, in mid-2025, I took the opportunity to migrate the whole project. It wasn't easy, it took
roughly one week per project,[^2] but moving to a standard solution felt like finally having solid
ground under my feet.

Ironically, the adapters I created to encapsulate Voyager's workarounds proved useful. 
They decoupled the client project from the navigation library itself, so the migration mostly 
involved changing those internal classes: instead of wrapping a `Navigator` instance, they wrapped 
around a `NavController`.

But the story didn't end there. By late 2025, I planned to add desktop support and improve layouts
for tablets and foldables. This meant dealing with **Adaptive Layouts**: representing master-detail
as two separate screens on small devices but as a two-pane single screen on larger ones (using
`ListDetailPaneScaffold`).

In Navigation Compose, I ended up with two different navigation graphs: dedicated two-pane
destinations for large screens and separate destinations for mobile.

## Enter Navigation 3

Meanwhile, Google released **Navigation 3**. Its biggest advantage was giving the client app full
ownership of the backstack. It also introduced `SceneStrategy`, which decoupled navigation entries
from their layout strategy. This meant master and detail destination definitions could remain
independent of how they were presented.

Navigation 3 entered CMP in version 
[1.10.0](https://blog.jetbrains.com/kotlin/2026/01/compose-multiplatform-1-10-0){ target=_blank } 
at the start of 2026. Roughly a year after moving to Navigation Compose, I decided to
re-migrate everything to Navigation 3.

For those interested, here is my migration checklist:

- ensure all destinations comply with the `NavKey` interface and setting up
  `SavedStateConfiguration` for multiplatform state restoration;
- replace old constructs (`NavHost`, `NavController`, `NavGraph`) with the new ones (`NavDisplay`,
  `NavBackStack`, `NavEntryDecorator`, `SceneStrategy`);
- remove `ListDetailPaneScaffold` usages and cleaning up the dual graph definitions;
- tests, tests and more tests (both automated and manual ones).

This time, my adapters turned against me. The recommended approach for Navigation 3 is to use a
`NavigationState` holder and a `Navigator` class (see the
[migration guide](https://developer.android.com/guide/navigation/navigation-3/migration-guide#step-3){ target=_blank }),
whereas I was relying on `NavigationCoordinator` and `NavigationAdapter`. 

Eventually I chose to keep my existing surface API but updated the internal implementation, which
was also a great excuse to polish some "encrustations of time" like an old throttling mechanism
which was not needed any more.

## Lessons Learned

If I could go back to 2023, here is what I'd tell myself:

1. **Standardization > Easiness:** A compact library might save time today but cost months tomorrow.
2. **Mind compromises:** Don't pick a library that treats deep links as an afterthought, choices
   can backfire.
3. **Build Adaptive from Day One:** Even if you're only on mobile now, you'll want those extra 
   pixels later.
4. **Be prepared to change:**  As already stated in other articles in this blog, changes are
   inevitable, so embrace them as improvement opportunities.

[^1]: Expect/actual classes and interfaces are in Beta. You can opt in with the compiler flag
`-Xexpect-actual-classes`.
[^2]: Specifically, migrating both the Mastodon/Friendica client and the Lemmy client.

*[UI]: User interface
*[DI]: Dependency Injection
*[JVM]: Java Virtual Machine
*[URI]: Uniform Resource Identifier
*[CMP]: Compose Multiplatform
*[AGP]: Android Gradle Plugin
*[API]: Application Programming Interface
