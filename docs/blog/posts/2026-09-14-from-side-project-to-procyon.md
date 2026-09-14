---
title: "From side project to Procyon: a look behind"
date: 2026-09-14
tags:
  - procyon
---

As we are approaching the last quarter of the year, it is normal for me to look back at all that has
been done in the last months, evaluate the current state of the project and plan for the future.

## Live fast, and eat trash (a.k.a. reuse code)

The Raccoon client for Mastodon and Friendica was started during the summer of 2024, as a spin-off
of the Lemmy client, whose development had started approximately a year before. The initial core
was assembled in a short time, taking heavy inspiration from the already established app, trying to
reuse as much as possible of the existing code.

The strategy was, more or less: take the existing architecture, switch the APIs to be compatible
with Mastodon/Friendica instances, rewrite the content rendering engine (since Lemmy posts were
Markdown-based while Mastodon posts are HTML-based), distribute it fast and see how it is received
by the public.

There were minimal variations in the tech stack, e.g. using Room multiplatform instead of
SQLDelight for local persistence, using Sentry for crash reporting (instead of saving traces on
device and rely on manual reports), using Mokkery in `commonTest` instead of MockK on
`androidHostTest`.

## KMP in its infancy: the technical debt

As a result, large portions of technical debt inherited from an era when KMP was immature
were still sitting there:

- localization used Lyricist (instead of the CMP resource system);
- DI using Koin with classic DSL (despite annotations being available);
- Voyager navigation (despite Compose Navigation being ported to multiplatform);
- endpoint calls used Ktorfit (breaking at every KSP update);
- setting encryption still used deprecated `EncryptedSharedPreferences` on Android;
- build configuration was scattered in individual scripts across subprojects, with subsequent
  duplication and potentially subtle inconsistencies.

## Paying it back: modernizing the stack

Starting from the end of 2024 and for most of 2025, besides reaching feature parity with existing
clients and fixing bugs, I always allocated a great amount of time to evolve the project and pay off
that debt.

At the beginning of 2026, the situation was the following:

- resources and localization had been ported to the built-in CMP system, but icons still relied on
  Google's Material Icons set, which has been deprecated in the meantime;
- DI was ported to Koin with KSP annotations, breaking reproducible builds, so Kodein was introduced
  as a temporary replacement until a more modern solution became available;
- the navigation system was ported to Compose Navigation with regular AndroidX `ViewModel`s;
- network calls were implemented with plain Ktor, no need for Ktorfit adapters;
- a custom layer for preference encryption on Android was introduced;
- build logic was centralized in a set of convention plugins, consistently applied throughout all
  subprojects for configuration.

## Breaking changes and the road to 1.0

In the meantime, Gradle 9 and AGP 9.x were released, making the project's very structure and most
of the build logic obsolete.

Therefore, a series of tough decisions had to be made in order to keep the project "healthy" and
release a stable 1.0.0 version:

- revamp the project's website and documentation to get a more modern and functional look (using
  Zensical);
- update Gradle and AGP, aligning with the now recommended project structure;
- add the JVM target and introduce desktop (with .deb package published at every stable release);
- introduce support for tablets and large screens using adaptive layouts;
- replace Material Icons with Material Symbols.

And that was exactly what I prioritized for version 1.0.0, which was released on June 8th, 2026.

## Beyond 1.0: surfing the KMP bleeding edge

After the first stable release, I started working on the areas which still needed improvement, such
as:

- Navigation 3 with the concept of `Scene` and `SceneStrategy` offers a much more elegant solution
  to the problem of adapting navigation and screen layout to the available screen size;
- a new player entered the DI scene on KMP: Metro, which finally promises to solve all the issues of
  previous solutions: conciseness, compile-time safety, power and flexibility;
- a new unified `@Preview` annotation to be used in common code was introduced, making it easier to
  integrate tooling previews.

Those were essentially the areas where I have been working in the last months, alongside
a lot of code cleanup and keeping an "aggressive" update strategy for Kotlin versions (adopting new
features as they emerged, e.g. explicit backing fields or the unused return type checker).

New features were added as well, in the meantime, such as:

- support for quote posts on Mastodon;
- content translation with LibreTranslate;
- implementing cross-instance exploration;
- adding themes for the reply bar in forum view;
- improve search and suggestion functionality with the transition to `/v2` endpoints.

## What now? A look ahead

What can be expected for the immediate future, then? First of all, I finally feel that I have a
solid foundation to build upon, thanks to the modernization effort I've engaged in.

For sure, on the feature side, support will be added for new Mastodon 4.6 features, such as
user collections.

On the UX side, I'd like to leverage some new features of CMP (e.g. `Grid`) for better attachment
rendering (instead of the existing carousels).

Finally, tech-wise, I am looking forward to the moment when rich errors are introduced in the
language. In the meantime, I still have to adopt other features already in preview like name-based
destructuring.

* [HTML]: HyperText Markup Language
* [KMP]: Kotlin Multiplatform
* [CMP]: Compose Multiplatform
* [DI]: Dependency Injection
* [KSP]: Kotlin Symbol Processor
* [AGP]: Android Gradle Plugin
* [JVM]: Java Virtual Machine
