---
title: "From mobile to desktop: our journey towards a new platform"
date: 2026-05-28
tags:
  - kmp
  - procyon
---

*Published on May 28, 2026*

## Premise

KMP is a remarkably flexible and unintrusive technology: it allows you to choose exactly which code
to share between platforms without forcing any specific architectural patterns on you.

We've discussed its key advantages and why it's a compelling choice for building Fediverse apps in
a [previous post](2025-06-10-building-modern-fediverse-apps.md).

## Self-deception

However, Raccoon has always been highly Android-centric. For a long time, I targeted only one
platform, tested only on Android, and distributed only via APKs and app bundles. iOS-specific
code was written where necessary during development, but it often felt like a shot in the dark.

Occasionally  I would run a debug build on an iOS simulator just to gaslight myself into believing
this was a truly multiplatform project, even when all factual evidence pointed to the contrary.

## Reality check

But sooner or later, the moment to face reality arrives. For me, it came when I was invited to speak
at this year's [DevConf 2026](https://devconf.it/2026) about Raccoon. I only had one issue: the app
had mostly seen bug fixes and maintenance over the last year. As I was heading toward a stable 1.0
release, I lacked "cool and shiny" new features to showcase at a conference.

Then it hit me: I could finally fulfill the requests from my Linux users and build a desktop
version. This would allow me to present not just a mobile app, but a cross-platform open-source
client for the federated social web.

I decided to dedicate a weekend or two to porting the app, promising to pivot if it didn't feel
worth the effort. To my surprise, after just one night of work, I had a macOS app running. It was
missing features and the layout was… questionable, but it worked and this was enough to give me fuel
to continue the work!

!!! info "Spoiler"
    We eventually decided to switch topics for the conf (stay tuned!) but Raccoon will still 
    be making an appearance!

## Our journey towards a new platform

In this article, I'll share my journey of supporting a new platform, highlighting the most critical
hurdles I encountered. Here's what we'll cover:

- Build configuration
- Adding an entry point
- Native implementations
- The OAuth2 Challenge

### Build configuration

This boiled down to configuring the `org.jetbrains.kotlin.multiplatform` Gradle plugin for a new
target. Luckily, Raccoon uses convention plugins (more on this in a future post). Since I already
had a`com.livefast.eattrash.kotlinMultiplatform` plugin, I only had to add `jvm()` within a single
extension function to automatically update over 50 subprojects.

Admittedly, there's a bit more to it since I'm also using CMP. Similarly, I
updated my `com.livefast.eattrash.composeMultiplatform` convention plugin to adapt the UI layer
across all affected subprojects.

### Adding the entry point

Every Java-based application needs an entry point. I initially created a `main()` function in the
`:shared` subproject, but later moved it to a dedicated `:desktopApp` module. This aligned the
project structure with the new JetBrains defaults, which we discussed in
a [previous post](2026-05-17-dealing-with-kmp-shifting-sands.md).

### Native implementations

This is where I spent the bulk of my time: adding `actual` versions for all `expect` declarations.

Sometimes this was trivial because I used libraries that already offered JVM support, such as:

- [Room](https://developer.android.com/kotlin/multiplatform/room)
- [Coil](https://github.com/coil-kt/coil)
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)
- [Sentry](https://sentry.io/welcome/)
- and all the rest…

Other times, it was difficult because no JVM equivalent existed for specific mobile
functionalities (like [Moko-permissions](https://github.com/icerockdev/moko-permissions)
or [UnifiedPush](https://unifiedpush.org/)).

Even seemingly simple tasks turned out to be tougher than expected. For instance, reading app
metadata (version and build number) is intrinsically coupled with how the application is packaged
for each specific platform.

### The OAuth2 Challenge

The OAuth2 login flow deserves its own spotlight. Typically, you redirect the user to an external
provider in a webview, they authenticate, and then redirect back with a code you exchange for a
token.

On mobile, this relies on deep linking (custom URI schemes) so the OS knows to pass the data back to
your app. Web apps just point back to a backend server.

But what about desktop apps? I initially tried using
the [Calf](https://github.com/MohamedRejeb/Calf) webview, but it struggled with intercepting the
redirects needed for the OAuth2 flow.

The solution? I leveraged [Ktor](https://ktor.io) — already in use for network calls—to spin up an
ephemeral local server. The redirect URI points to `localhost` on the first available port. The
server stops as soon as the request is intercepted or after a short timeout. It turned out to be an
elegant and seamless solution.

## Conclusion

This journey was incredibly enriching. It pushed me to improve the layout for larger screens,
which—as a side benefit — greatly improved the experience for mobile users on tablets and foldables.

While it might seem like a small addition, providing a `.deb` package for every release is a win for
the open-source community. Having a dedicated desktop Fediverse client helps the ecosystem grow, and
I'm excited to see where the project goes next.

*[KMP]: Kotlin Multiplatform
*[CMP]: Compose Multiplatform
*[DB]: Database
*[UI]: User Interface
*[URI]: Uniform Resource Identifier
*[OS]: Operating system
