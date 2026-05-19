---
title: "Dealing with KMP’s Shifting Sands: Another Day, Another Project Structure"
date: 2026-05-17
categories:
  - kmp
tags:
  - kmp
---

*Published on May 17, 2026*

**Hurray!** A few days ago, JetBrains's blog published
a [new post](https://blog.jetbrains.com/kotlin/2026/05/new-kmp-default-structure) highlighting the
new default structure for KMP projects, which goes hand in hand with the migration to AGP 9.x.

This piece of news hit me last Friday evening. I had just left the office after a long, tiring work
week. I got into my car to drive home and opened YouTube, hoping to listen to a relaxing talk during
my commutetime… and voilà! There was Márton Braun uttering the frightening words: *«We are
introducing a new default structure for KMP projects…»*

And so there I was — suffering from framework-induced PTSD, nervously blinking and hysterically
chuckling alone in my car. I remembered how it took me several days to get my local builds for all
platforms, CI workflows, and integrations (Codecov and Weblate, just to mention a couple) up and
running correctly.

How on earth is the recommended structure changing *again*?!

## Backstory: breaking changes in AGP 9.0

To fully understand my despair, a little bit of background is needed.

Earlier this year, I migrated from Gradle 8.14.x to Gradle 9.1, and
[from AGP 8.11 to AGP 9.0.0](https://kotlinlang.org/docs/multiplatform/multiplatform-project-agp-9-migration.html).

For KMP projects like mine, this meant it was no longer possible to apply the
`org.jetbrains.kotlin.multiplatform` Gradle plugin alongside the `com.android.library` or
`com.android.application` plugin in the same module.[^1]

Instead, the new`com.android.kotlin.multiplatform.library` plugin was introduced.
Now, all shared modules must use the new KMP library plugin, while the main Android entry
point has to use `com.android.application` alone.

The previous approach — where all entry points (except iOS which was in a separate folder in order
to be opened as an Xcode project) coexisted in a single `composeApp` module — was no longer
viable.

Therefore, I had to create a shared library module, making the Android application a separate module
entirely. Ironically enough, this feels a lot like the standard structure we had back in 2023.

I felt relatively lucky because only my *Raccoon for Friendica* app used the structure they
recommended after 2023. My *Raccoon for Lemmy* app still had a shared module and a separate
`androidApp` module.

But the story doesn't end there.

[^1]: A note on terminology: in the rest of this article, the word "module" is used as a synonym of
subproject for brevity, even though in the Gradle lingo the most correct term is "subproject".

## Ripple effect: broken tests and displaced resources

AGP 9.x also fundamentally changes the source set structure for common unit tests (running on the
host system) and instrumented tests (running on a device/simulator).

In my case, I used convention plugins, meaning I apply configurations via the Gradle Kotlin DSL and
various plugin DSLs rather than doing it manually.

Trying to figure this out with incredibly scarce and scattered documentation on how to do it
properly was a nightmare (and I had to fix it (e.g.
[here](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/pull/1192) and
[here](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/pull/11923)).

Not to mention that all Compose resources such as string translations had to be moved from the
`composeApp` mobile module to the `shared` module, which completely broke my crowdsourced
translations on [Weblate](https://hosted.weblate.org/engage/raccoonforfriendica).

## A love-hate relationship

I am truly caught in a love-hate relationship with KMP right now. More than ever, it feels like a
brittle foundation to build a long-term project upon.

*«Why are you complaining?»* you might ask. *«After all, early-adoption has always been like this.»*
And you're right. In the early years, this chaos was expected. Every new technology needs time to
settle down until the community reaches a consensus on best practices and shared conventions.

But KMP has been around for quite some time now, and it is supposed to be stable! Yet here we are,
and it feels like they introduce breaking changes every six months.

## Looking ahead

So here I am **once again** trying to follow their new standard as closely as possible. I'm doing
this in the desperate hope of minimizing the chances of everything melting down with the next
release of the Kotlin compiler, KSP, Gradle, AGP, Compose, or whatever else updates next.

At the very least, I’m hoping that by strictly adhering to these recommendations now, the next
migration will be less painful. It feels like offering sacrifices to a vengeful deity,
praying that my compliance today will earn me a little more mercy when the next storm hits.

As a conclusion, I hope you allow me this little rant. Also, please wish me luck—because as of right
now, I have no idea if my iOS build is actually working, and I won't know if any of my release
workflows are truly safe until my next beta deployment.

*[KMP]: Kotlin Multiplatform
*[AGP]: Android Gradle Plugin
*[PTSD]: Post-Traumatic Stress Disorder
*[KSP]: Kotlin Symbol Processor