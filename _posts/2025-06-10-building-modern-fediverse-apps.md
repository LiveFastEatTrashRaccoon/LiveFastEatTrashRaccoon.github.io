---
layout: post
title: "Building modern Fediverse apps in KMP & Compose: the perfect match"
date: 2025-06-10
tags: ["kmp", "compose", "community"]
---

The Fediverse represents the future of social media—decentralized, open, and user-controlled. As
platforms like Mastodon, Friendica, and Lemmy gain momentum, developers face a crucial question:
how do you build modern, cross-platform applications that can keep pace with this rapidly evolving
ecosystem?

The answer lies in Kotlin Multiplatform (KMP) combined with Compose Multiplatform (CMP) — a
technology stack that is uniquely suited to solve what I refer to as «Fediverse development
challenge».

## The Fediverse development challenge

### Platform fragmentation

Modern users expect native experiences across mobile, desktop, and web platforms. Traditional
approaches usually force developers to choose between:

- building separate native apps (which is expensive and slow to iterate)
- creating web-only solutions (with the disadvantage of limited platform integration )
- using older cross-platform frameworks for app development (which may imply performance tradeoffs
  or increase the side of the app package).

### Protocol complexity

Fediverse protocols like ActivityPub are intricate, requiring sophisticated networking, JSON
parsing, proper state management and a well-designed and future-proof implementation.

Duplicating this logic across multiple platforms introduces maintenance overhead for sure, plus it
may potentially lead to bugs.

### Rapid evolution

The Fediverse moves fast: new features, protocol extensions, and federation behaviors emerge
regularly. The development stack needs to support quick iterations and seamless updates across
all platforms, in order to adapt to changes rapidly, release quickly and reach the widest possible
audience.

## Why Kotlin Multiplatform?

### Share what you want, when you want

KMP is flexible and allows you to choose what to share: for example, you can share the complex
business logic — ActivityPub implementation, federation handling, data models, and networking —
while keeping platform-specific UI native. Otherwise, you can also share some UI parts or, if you
are brave enough, all of it with Compose Multiplatform (CMP) as we do in Procyon.

The key concept is, it is not an "all or nothing" choice where you either opt-in or opt-out
completely at once, you can gradually choose what to share and when to do it, which gives you as a
developer great power (even to rollback some of your decisions if you want to).

The `expect`/`actual` mechanism, moreover, makes it possible to access underlying operating system
features (such as filesystem, gallery, camera, video playback, push notifications, inter-process
communication e.g. sharing data to other apps or receive data from other apps through deep links)
when you need them, defining your own abstractions to call from common code.

### Type-safety

Kotlin's strong type system and sealed classes make it excellent for modeling ActivityPub entities
and activities. You can create robust, compile-time-safe representations of posts, actors, and
interactions that work identically across all platforms.

There are even libraries,
like [LemmyBackwardsCompatibleAPI](https://github.com/MV-GH/LemmyBackwardsCompatibleAPI) which offer
abstractions for Lemmy data types and a unified adapter to endpoints which make it transparent for
client-apps developers which version of the backend is used by the current instance.

### Networking and serialization

Libraries like [Ktor](https://ktor.io/), [Ktorfit](https://foso.github.io/Ktorfit/) and
[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) provide first-class
support for HTTP clients, API modeling and JSON parsing — essential for Fediverse applications that
constantly communicate with various servers and handle complex data.

Using these three libraries in conjunction (with KSP, the Kotlin Symbol Processor) makes it
extremely simple to define endpoint calls, handling asynchronous result and errors in an idiomatic
way. Dealing with KSP in a multi-module and multi-platform project may pose some difficulties,
nonetheless, stay tuned for updates on this in future posts.

### Coroutines and structured concurrency

Kotlin coroutines excel at handling the asynchronous, real-time nature of social media
apps. Parent-child relationships between tasks, when background operations are tied with the
lifecycle of a screen, allow to gracefully cancel tasks when their result is not needed, saving
battery power and limiting data transmission over the network.

## Compose Multiplatform: a UI that scales

### Declarative UI for complex social interfaces

Social media interfaces are inherently complex — timelines, media galleries, thread
visualizations for discussions, and real-time updates. The declarative approach of Compose
Multiplatform makes these interfaces easier to build and maintain than traditional imperative UI
frameworks.

### Consistent design systems

Fediverse apps benefit from consistent branding and behavior across platforms. Compose Multiplatform
lets you implement your design once and deploy it everywhere, ensuring users get the same
experience (no matter whether they're on Android, iOS, desktop, or web).

Material Design 3 is a modern and robust design system which ensures consistency, accessibility and
customization options.

### Performance where it counts

Unlike web-base cross-platform solutions, Compose Multiplatform compiles to native code, providing
the smooth scrolling and responsive interactions that social media users expect, which are
especially important to make the Fediverse as engaging as possible for users and promote its
adoption.

## Real-World advantages for Fediverse development

### Faster federation support

When new ActivityPub extensions or features emerge, you can implement them once in shared KMP
code and immediately have support everywgere. This is crucial to keep up with the
fast-moving Fediverse ecosystem.

For example, Raccoon is going to provide Lemmy 1.x support to all its target platform (Android, iOS)
at once, see [this issue](https://github.com/LiveFastEatTrashRaccoon/RaccoonForLemmy/issues/349) to
monitor the state of integration.

### Simplified testing

Not only the implementation code is shared, with KMP you can also share tests. For business logic
this is pretty straightforward (more on this in future posts, especially for mocking libraries) —
whereas UI tests need a device to run (iOS simulator, Android emulator, etc.) but it is nonetheless
possible to write tests once and run them on multiple devices (more on
this [here](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html)).

### Community contributions

A single, well-structured Kotlin codebase is more approachable for open-source contributors than
maintaining separate applications. This matters for Fediverse projects that often rely on community
development, when the workforce is often limited to few volunteers.

### Leverage a rich ecosystem

The Kotlin and Compose Multiplatform ecosystem has matured significantly over the past few years and
at Procyon I've seen it grow from 2023 on.

Key libraries for Fediverse development include:

- [Calf](https://github.com/MohamedRejeb/Calf) for a web view implementation;
- [Coil](https://github.com/coil-kt/coil) for image loading;
- [Compose ColorPicker](https://github.com/skydoves/colorpicker-compose) for custom color selection;
- [Compose Multiplatform Media Player](https://github.com/Chaintech-Network/ComposeMultiplatformMediaPlayer)
  for video playback;
- [Connectivity](https://github.com/jordond/connectivity) to detect network state changes;
- [Kodein](https://github.com/kosi-libs/Kodein)
  or [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp/) for dependency injection;
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON (de)
  serialization;
- [Kover](https://github.com/Kotlin/kotlinx-kover) to calculate test coverage;
- [Ksoup](https://github.com/MohamedRejeb/Ksoup) for HTML parsing;
- [Ktor](https://ktor.io/) and [Ktorfit](https://foso.github.io/Ktorfit) for networking;
- [Mokkery](https://mokkery.dev/) to generate mocks which can be used in the `commonTest`;
- [Multiplatform Markdown Renderer](https://github.com/mikepenz/multiplatform-markdown-renderer) for
  markdown rendering;
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings) for encrypted shared
  preferences;
- [MaterialKolor](https://github.com/jordond/MaterialKolor) for custom theme generation;
- [Moko-Permissions](https://github.com/icerockdev/moko-permissions) for runtime permission
  management;
- [Napier](https://github.com/AAkira/Napier) for logging;
- [Reorderable](https://github.com/Calvin-LL/Reorderable) for reorderable lazy lists in Compose;
- [Room](https://developer.android.com/kotlin/multiplatform/room)
  or [SQLDelight](https://github.com/cashapp/sqldelight)
  and [SQLCipher](https://github.com/sqlcipher/sqlcipher) for local persistence;
- [Sentry](https://sentry.io) for crash reporting and user feedback collection;
- [Turbine](https://github.com/cashapp/turbine) to test Flow (from kotlinx.coroutines);
- [UnifiedPush](https://unifiedpush.org) for push notifications;
- [Voyager](https://voyager.adriel.cafe/) for navigation.

## Conclusion

Building a modern Fediverse app requires balancing rapid development, protocol complexity,
and user experience across multiple platforms.

Kotlin Multiplatform and Compose Multiplatform provide the perfect foundation — letting you focus on
what makes your Fediverse app unique while sharing the complex domain code that makes your app work.

The Fediverse represents a return to user agency and open protocols. Your development stack should
embody those same principles: open, flexible, and designed for the long term. Kotlin Multiplatform
and Compose Multiplatform deliver exactly that.

--- 

*Ready to build the next great Fediverse app? The tools are here, the protocols are maturing, and
the community is waiting.*

{% include tag_footer.html %}
