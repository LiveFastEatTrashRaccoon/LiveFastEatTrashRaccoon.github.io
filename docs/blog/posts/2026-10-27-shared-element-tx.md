---
title: "Labor Limae: When \"Mostly Working\" isn't Enough"
date: 2026-10-27
categories:
  - a11y
  - ux
tags:
  - a11y
  - ux
---

In his Ars Poetica, Horace uses the term _labor limae_ to define the process by which artists refine
their work before publication as if progressively smoothing down sharp edges using a file.

Without any pretension to compare myself to the great Latin poet, I kind of resonate with the filing
work he describes, as I recently found several rough edges to be polished in my code, even though I
invested a lot of effort to modernize the project and keep it in a healthy state
(see [last month's article](2026-09-14-from-side-project-to-procyon.md){target=_blank}).

## Questionable Architecture

For example, I was recently scanning the codebase to find occurrences of position-based
destructuring to migrate to new name-based syntax. While doing so, I found a snippet to handle
translations duplicated several times, instead of creating a dedicated use case and invoke it.

In this category, it is worth mentioning the undisciplined use of Compose's composition local
mechanism accumulated over the years. For each of the custom ones, I even had to add an exclusion
rule for Spotless in build configuration. And even for library ones, their usage was sometimes
weird, e.g. `LocalDensity` (to set custom font scale) was provided in three different places with
the same exact source.

Another example was that post actions (the ability to reply, share, bookmark, etc.) were exposed
through a repository which was accessed directly from the UI layer. A more solid solution would have
been to save permissions in the UI model and populate them in the presentation or, even better,
pagination logic. The funny thing was that I had already handled custom emojis in the "correct" way,
so apart from a suboptimal architectural choice this was also an inconsistency.

## Code Inconsistencies

Speaking of inconsistencies, there were reducer functions in `ViewModel`s, where sometimes intent
was handled in place, sometimes it was delegated to a private handler method whose naming did not
follow a consistent pattern. Moreover, some of the handlers were suspend functions, while others
encapsulated asynchronicity inside the function body.

Another inconsistency I found was in assisted injection: sometimes assisted factories were
considered an implementation detail and extended a parent interface, sometimes they were exposed
directly to call sites. Considering the flexibility provided by the former pattern, I chose to
consistently adopt it everywhere: consider the assisted factory an implementation detail and bind it
to the public parent interface for clients.

## Poor Naming Choices

In computer science there are just two difficult things: cache invalidation, naming things… _and
off-by-1 errors_ (sic)!

Raccoon is no exception: there were some confusing legacy naming choices. Suffice it to say that
"paged lists" were called "lists with page cursor" and "posts" were called "timeline entries".

The reason why I chose to do so was, in the early days, to not adopt any backend-driven terminology
(e.g. Mastodon defines them as "statuses", Friendica calls them "items", etc.). However, my initial
choice was verbose and ultimately did not age well: it was too vague to convey any meaning and too
verbose.

## Overcomplication

In all cases where assisted injection was used were only allowed a single assisted param, using a
wrapper data class where multiple values were needed. This was a legacy of **Voyager's Koin
integration**, which somehow survived across the years, even if navigation was migrated first to
**Compose Navigation** and then to **Navigation 3**; while dependency injection switched to **Koin +
KSP annotations**, **Kodein**, **Kotlin + compiler plugin** and finally to **Metro**. There was no
longer any need to wrap values inside a data class and have a single assisted param.

Concerning navigation, I had another "WTF moment" when I realized that my abstractions[^1] were
beautifully architected with interface-implementation and DI annotations, but the concrete class was
manually instantiated where needed. And for a good reason, injection would have never worked since
there were constructor parameters which couldn't be pulled off Metro's graph as there were no
bindings for them (the correct approach was to pass them via assisted injection).

## Lack of optimization

Knowing that obfuscation, minification and resource shrinking, if not properly configured, could
lead to breakages in release builds, I left those optimizations disabled at the beginning of the
development stage. I thought "I'll enable them when I start publishing release builds", then time
passed, I started distributing to hundreds of users, and the options remained disabled.

It was not until Google Play warned me that the app could be delisted for not turning on advanced
protection that I found the time to turn them on, test and debug, and ship the optimized bundle. I
didn't even realize that the APK size, for distribution outside Google Play, had increased since the
adoption of Metro, which could be an issue for both third-party store maintainers and users who had
to download larger archives.

Considering app size, how about redundant resources? I found I had two variants of the "play" arrow
for videos, one with and one without an outlined circle. They are simple vector images, so do not
add that much to the final bundle size but, again, isn't it better to have just one?

## UX & a11y Pitfalls

Finally, there was a whole list of small interventions for things that got on my nerves every time I
used the app.

The search screen was opened with the keyboard opened by design. Ok, but it remained opened when
scrolling the lazy list, which felt weird and kept the focus away from the search results. Moreover,
worse still, when navigating to another screen (e.g. post detail) and going back to the search, the
keyboard reopened. This, again, felt weird and made the search feature feel clunky.

Then, the image detail screen. I designed the feature to handle multiple elements (with a horizontal
pager) and multiple media types (images and videos). This was cool, but the image viewer also had a
pan-to-zoom behavior, which conflicted with the page scroll gestures. The solution I came up with
two years ago, when my only goal was to complete the MVP, was to lock the zoom initially, and change
the zoom level with a double tap. Again, this felt unnatural, but it wasn't until last week that I
designed a better gesture hierarchy, passing scroll events to the pager unless zoomed in beyond 1x.

Still concerning images, Compose Multiplatform added shared element transitions from version 1.7.0
in October 2024. We're in October 2026 and there was still no use of it in Raccoon, because there
was always something more important to do. Well, users deserve better! Version 1.1.7 adds shared
element transitions when opening single images from post lists, post detail and user detail.

This was also an occasion to spot all the cases where `Modifier.clickable` was added to UI
components with an empty lambda, which resulted in extraneous ripple effects and accessibility tools
incorrectly identifying non-interactive elements as clickable.

And, finally, plain old bugs. For example, the reblog count was incorrect after adding or removing a
boost, the navigation drawer could lock in the closed state and never reopen, the list-detail
navigation was behaving incorrectly in some paths, predictive back did not always animate correctly
on mobile.

## Wrapping up

It's easy to let legacy code pile up when things work in "most cases". But true project health isn't
always about shipping new features: it's also about the mindset to go back, smooth down rough edges
and reconsider previous choices if the experience suggests so.

Refactoring, eliminating bugs and inconsistencies, and tightening up gestures and a11y isn't just a
technical exercise. It translates into a faster, smoother, and more user-friendly experience for
everyone.

Version 1.1.8 is the result of this "filing" work: lighter binaries, better navigation transitions,
intuitive media interactions and a cleaner foundation ready for whatever comes next.

!!! tip

    *Labor limae* is never really finished, but every pass brings us closer to the polished
    experience Raccoon users deserve.

[^1]: Which were used to encapsulate Voyager's `Navigator` first, then Compose Navigation's
`NavController` and finally Navigation 3's `NavBackStack`.

*[a11y]: Accessibility
*[UX]: User eXperience
*[KSP]: Kotlin Symbol Processor
*[DI]: Dependency Injection
*[WTF]: What The 🦆
*[MVP]: Minimum Viable Product
