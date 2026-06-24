package com.livefast.eattrash.rssgenerator.data

import com.livefast.eattrash.rssgenerator.core.DateUtils
import com.livefast.eattrash.rssgenerator.model.PostData

interface PostRepository {
    fun getAll(): List<PostData>
}

class PostRepositoryImpl(
    private val dateUtils: DateUtils
) : PostRepository {
    override fun getAll(): List<PostData> = listOf(
        PostData(
            title = """
One Preview to Rule Them All: Unified Compose Previews in Common Code""".sanitized(),
            summary = """
When working on a new technology like KMP and CMP, sometimes you run into sharp edges. One of 
them was Compose Multiplatform previews, requiring either tons of compromises or a setup nighmare. 
Sometimes, especially when only Developer Experience is involved, it is better to wait until the 
technology matures. In this article I'll explain how Preview changed with Compose Multiplatform 
1.10.0 and how finally adopting them helped me cleanup and improve code.""".sanitized(),
            lastSegment = "2026-06-24-unified-previews",
            date = dateUtils.normalized(year = 2026, month = 6, day = 24, hour = 8, minute = 30)
        ),
        PostData(
            title = """
The Raccoon's Dilemma: Balancing Features, Maintenance, and Sanity""".sanitized(),
            summary = """
Working on open-source apps and balance maintenance, experimentation and feature requests in one's 
spare time is not always an easy task. If people start pointing fingers and judge by the way social 
posts (rather than code) are written, we have a problem. In today's article I discuss why I keep 
working on side projects, how tasks are priorities and why some choices were reverted over time 
rather than just pile new features one on top of the other.""".sanitized(),
            lastSegment = "2026-06-20-oss-sustainability",
            date = dateUtils.normalized(year = 2026, month = 6, day = 20, hour = 16, minute = 50)
        ),
        PostData(
            title = """
Breaking Language Barriers: Client-Side Translation in the Fediverse""".sanitized(),
            summary = """
The Fediverse is built on the promise of decentralized communication. However, when users from 
different instances federate, they often encounter a barrier that code alone can not fix: language. 
Some platforms are designed to integrate with external translation providers, but this has 
downsides for instance admins (cost and/or maintenance overhead). In this article we'll discuss how 
managing translation on clients may help distribute costs and let users configure their favorite 
service.""".sanitized(),
            lastSegment = "2026-06-11-breaking-language-barriers",
            date = dateUtils.normalized(year = 2026, month = 6, day = 11, hour = 22, minute = 19)
        ),
        PostData(
            title = """
Beyond contentDescription: Accessibility in Compose Multiplatform""".sanitized(),
            summary = """
Beyond contentDescription, this article explores some techniques to improve accessibility in 
Compose Multiplatform apps. Using Raccoon as a case study, it covers design ideas for better 
screen reader integration, ensuring content parity and preserving semantics integrity. It 
highlights that a11y is a collaborative effort: together we can make the Fediverse more inclusive 
and usable for everyone.""".sanitized(),
            lastSegment = "2026-06-08-advanced-accessibility-in-cmp",
            date = dateUtils.normalized(year = 2026, month = 6, day = 8, hour = 20, minute = 40)
        ),
        PostData(
            title = """
Multi-module architecture and shared build logic""".sanitized(),
            summary = """
Gradle is the de facto standard build tool for KMP projects and for a reason: it is extremely 
powerful and highly flexible. However, if not configured correctly, it may result in suboptimal 
build performance or its configuration may become hard to maintain over time. In this article we'll 
discuss what are the main issues I experienced and what solutions I adopted to mitigate them in 
Raccoon; namely its module structure and the convention plugins it uses.""".sanitized(),
            lastSegment = "2026-06-06-multi-module-architecture",
            date = dateUtils.normalized(year = 2026, month = 6, day = 6, hour = 16, minute = 15)
        ),
        PostData(
            title = """
From mobile to desktop: our journey towards a new platform""".sanitized(),
            summary = """
Did you notice that in the latest beta versions, besides APKs to install on Android a .deb package 
has popped up to be installed on Debian-based Linux distros? This is not by chance: thanks to 
Kotlin Multiplatform's power, Raccoon has been ported to JVM and can now run as a desktop app. In 
this post I'll shortly describe how this came to be, from ideation to implementation; which were 
the main challenges I found in the migration process and what potential implications it has on the 
project and its users.""".sanitized(),
            lastSegment = "2026-05-28-from-mobile-to-desktop",
            date = dateUtils.normalized(year = 2026, month = 5, day = 28, hour = 23, minute = 5)
        ),
        PostData(
            title = """
Bringing Push Notifications to Raccoon""".sanitized(),
            summary = """
One of the most common complaints during Raccoon's first year of development was the lack of 
support for push notifications. Some time was needed to find the best solution for privacy, 
flexibility and transparency. UnifiedPush turned out to be a game changer: its innovative 
architecture makes it possible to provide notifications without sacrificing freedom and security. 
Through open standards like WebPush it is compatible with Mastodon and Friendica servers, and its 
SDK allows to remain 100% FOSS on the client. No more excuses to not try the app!""".sanitized(),
            lastSegment = "2026-05-20-bringing-push-notifications",
            date = dateUtils.normalized(year = 2026, month = 5, day = 20, hour = 22, minute = 25)
        ),
        PostData(
            title = """
Dealing with KMP’s Shifting Sands: Another Day, Another Project Structure""".sanitized(),
            summary = """
JetBrains has announced they are introducing a new default project structure for KMP projects. 
With the new structure all apps have separate entry points which is a lot cleaner and more 
flexible. But, at the same time, this is just another change, after a lot of breaking changes 
have been introduced earlier this year with AGP 9.x. Building on top of KMP is cool, but sometimes 
it feels like walking on shifting sands, with the risk of major refactorings being needed every 
few months.""".sanitized(),
            lastSegment = "2026-05-17-dealing-with-kmp-shifting-sands",
            date = dateUtils.normalized(year = 2026, month = 5, day = 17, hour = 9, minute = 24)
        ),
        PostData(
            title = """
Create Adaptive Designs with Window Size Classes""".sanitized(),
            summary = """
Raccoon's UI was adapted to better leverage the available space in large screens, e.g. foldables, 
tablets and – of course – the new desktop app. Not only elements have been moved to be to feel 
native on larger screens, e.g. using a permanent navigation drawer instead of the bottom 
navigation; but also using canonical layouts such as List-Detail pattern with animated transitions. 
This is just the beginning of the transition, because more changes and a better UX is going to come 
with the switch to Navigation 3.""".sanitized(),
            lastSegment = "2026-05-15-create-adaptive-multiplatform-designs",
            date = dateUtils.normalized(year = 2026, month = 5, day = 15, hour = 6, minute = 0)
        ),
        PostData(
            title = """
Better documentation with Zensical""".sanitized(),
            summary = """
I've updated this blog as well as the user manuals and websites of the apps to Zensical, the 
evolution of MkDocs which provides integrated search (with categories), theming, a more 
user-friendly navigation and a multilingual setup. The switch from Jekyll was easy and the results 
are awesome, with built-in support for callouts, collapsibles, tabs, syntax highlighting, formulas 
and much more.""".sanitized(),
            lastSegment = "2026-05-11-better-documentation-with-zensical",
            date = dateUtils.normalized(year = 2026, month = 5, day = 11, hour = 21, minute = 6)
        ),
        PostData(
            title = """
Why I decided to migrate away from the Ktorfit library""".sanitized(),
            summary = """
I recently migrated all of my projects away from the Ktorfit library. The balance between cost and 
benefits simply went off due to continuous breakages whenever new KSP and Kotlin versions were 
released. With no stable compiler APIs and breaking upstream changes, it is difficult for library 
maintainers to keep up, but this is absolutely no excuse for being rude to other developers and 
potential contributors. Working on open source projects is a collaborative task. Failing to 
understand this means your project is not going to survive.""".sanitized(),
            lastSegment = "2025-07-09-ktorfit",
            date = dateUtils.normalized(year = 2025, month = 7, day = 9, hour = 13, minute = 35)
        ),
        PostData(
            title = """
Hold my state: why shared ViewModels are a life-changer""".sanitized(),
            summary = """
When the Raccoon apps were created AndroidX ViewModels were not ported to KMP so the only viable 
solution was using 3rd party libraries for state holding, such as Voyager. But having ViewModels 
available outside the Android source set was a life changer and made it possible to completely 
rework the app's navigation in order to get a more robust, standard compliant and flexible result. 
Embracing change, constantly refactoring and evolving in order to adapt as new scenarios emerge 
makes us better software architects and our users hopefully happier.""".sanitized(),
            lastSegment = "2025-07-04-hold-my-state",
            date = dateUtils.normalized(year = 2025, month = 7, day = 4, hour = 18, minute = 48)
        ),
        PostData(
            title = """
Playful themes: what's in a name?""".sanitized(),
            summary = """
Localization and customization are first-class citizens at Procyon. One feature in which they come 
together is the choice of the color theme to apply to the app, with playful alliterating names 
that translate uniquely across different languages.""".sanitized(),
            lastSegment = "2025-07-01-playful-themes",
            date = dateUtils.normalized(year = 2025, month = 7, day = 1, hour = 21, minute = 15)
        ),
        PostData(
            title = """
Koin VS Kodein: a developer's journey through multiplatform DI hell""".sanitized(),
            summary = """
Dependency injection has been the backbone of Android development for years. But when you venture 
into Kotlin Multiplatform (KMP) territory, the comfortable world of Dagger and Hilt suddenly 
becomes unavailable. This is the story of my two-year journey building Raccoon apps and how I 
learned the hard way that not all DI solutions are created equal.""".sanitized(),
            lastSegment = "2025-06-27-koin-vs-kodein-for-kmp-di",
            date = dateUtils.normalized(year = 2025, month = 6, day = 27, hour = 10, minute = 0)
        ),
        PostData(
            title = """
Swipe Navigation: turn your Fediverse feed into a page-turner""".sanitized(),
            summary = """
Tired of the endless tap-back-tap dance? Swipe navigation transforms your feed into a flowing 
narrative where each post is simply the next page in your story. No more navigation gymnastics, 
no more losing your scroll position, no more breaking your reading flow.""".sanitized(),
            lastSegment = "2025-06-24-swipe-navigation",
            date = dateUtils.normalized(year = 2025, month = 6, day = 24, hour = 14, minute = 30)
        ),
        PostData(
            title = """
Global localization: Building a truly international open source app""".sanitized(),
            summary = """
Enabling users to interact with software in their native language isn't just a nice-to-have 
feature, it's essential for true accessibility and inclusion. Learn how a humanities background 
shaped the technical decisions behind Raccoon's multilingual journey and our community-driven 
approach to localization.""".sanitized(),
            lastSegment = "2025-06-20-i10n-and-l10n-challenges",
            date = dateUtils.normalized(year = 2025, month = 6, day = 20, hour = 9, minute = 0)
        ),
        PostData(
            title = """
What happened to Raccoon for Lemmy?""".sanitized(),
            summary = """
Many people have wondered what happened during August 2024 to the Raccoon for Lemmy app. The 
original repository was completely shut down overnight. This article recaps the situation before 
and after the change to clarify things for the community.""".sanitized(),
            lastSegment = "2025-06-17-what-happened-to-raccon-for-lemmy",
            date = dateUtils.normalized(year = 2025, month = 6, day = 17, hour = 18, minute = 0)
        ),
        PostData(
            title = """
Friendica: the Swiss Army knife of the Fediverse""".sanitized(),
            summary = """
While most Fediverse platforms force you to choose between microblogging, photo sharing, or link 
aggregation, Friendica refuses to make you pick just one. Learn about the universal federation hub 
that brings together all of the Fediverse into one experience.""".sanitized(),
            lastSegment = "2025-06-13-what-makes-friendica-shine",
            date = dateUtils.normalized(year = 2025, month = 6, day = 13, hour = 10, minute = 0)
        ),
        PostData(
            title = """
Building modern Fediverse apps in KMP & Compose: the perfect match""".sanitized(),
            summary = """
Building modern, cross-platform applications for the rapidly evolving Fediverse ecosystem 
requires a powerful stack. Discover why Kotlin Multiplatform and Compose Multiplatform are the 
perfect match for solving the unique challenges of Fediverse development.""".sanitized(),
            lastSegment = "2025-06-10-building-modern-fediverse-apps",
            date = dateUtils.normalized(year = 2025, month = 6, day = 10, hour = 15, minute = 30)
        ),
        PostData(
            title = """
Contributing to Raccoon apps""".sanitized(),
            summary = """
The Raccoon apps embody the spirit of open-source collaboration. At Procyon, every contribution is 
welcome and everyone's opinion is valued. Learn about our philosophy, our R.A.C.C.O.O.N. code of 
conduct, and how you can join us in building better tools for the Fediverse.""".sanitized(),
            lastSegment = "2025-06-08-contributing-to-raccoon-apps",
            date = dateUtils.normalized(year = 2025, month = 6, day = 8, hour = 11, minute = 0)
        ),
        PostData(
            title = """
Welcome to the Procyon Project!""".sanitized(),
            summary = """
Welcome to the official blog of the Procyon Project! Learn about our vision for decentralization, 
our Kotlin Multiplatform apps (Raccoon for Lemmy and Raccoon for Friendica), and why digital 
freedom matters in today's social media landscape.""".sanitized(),
            lastSegment = "2025-06-07-welcome-to-procyon",
            date = dateUtils.normalized(year = 2025, month = 6, day = 7, hour = 9, minute = 0)
        ),
    )

    private fun String.sanitized(): String = trimIndent().replace("\n", "")
}
