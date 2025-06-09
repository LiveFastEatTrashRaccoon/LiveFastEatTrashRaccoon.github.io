---
layout: post
title: "What happened to Raccoon for Lemmy?"
date: 2025-06-17
tag: lemmy community 
---

Many people have wondered what happened during August 2024 to the Raccoon for Lemmy app. The
original repository was completely shut down overnight, and development continued in what was then
the main fork—fortunately updated to the latest commit before the shutdown.

The fork was initially owned by a contributor called N7-X (who had already submitted multiple PRs
upstream during the previous months), then transferred to new ownership and hosted within
an [organization](https://github.com/LiveFastEatTrashRaccoon), with N7-X and Akesi Seli as main
contributors.

What happened, and more importantly, why? This article recaps the situation before and after the
change to clarify things for the community.

## Just a side project…

The app was initially
hosted [here](https://web.archive.org/web/20240629233017/https://github.com/diegoberaldin/RaccoonForLemmy)
(latest available snapshot from the Internet Archive) and had been primarily developed since
mid-2023 by a developer from Italy as a side project to experiment with Kotlin Multiplatform
technology.

External contributions were always welcome. During spring 2024, N7-X began contributing, starting
with smaller tasks and gradually taking on more significant features—including Markdown
support—eventually becoming a *de facto* co-maintainer.

## The Markdown benchmark incident

On August 1st, 2024, [this post](https://lemmy.world/post/18159531) was created in
the [Lemmy Apps](https://lemmy.world/c/lemmyapps) community on lemmy.world. The moderator conducted
a benchmark evaluating Markdown rendering across different Lemmy clients, scoring each app based on
[predefined test cases](https://lemmy.world/comment/11514952).

The benchmark's goal was constructive: raise awareness about Markdown rendering issues so the
community could work together to improve the Lemmy ecosystem during a critical period when the user
base was fragmented across various competing platforms.

However, Raccoon had issues rendering tables correctly, and some format checks incorrectly
evaluated text within poorly-rendered tables. This caused the app to be «marked down twice when it
shouldn't have» ([see here](https://lemmy.world/comment/11516297)), resulting in an initially very
low score (not even 5 out of 10), before the post was updated.

## The unintended consequences

What followed was unexpected and unfortunate. People began complaining about Raccoon's poor
performance, others suggested migrating away from the app, and the main developer started receiving
notifications and negative reviews (during his summer vacation).

He suddenly realized his real identity was publicly visible and that the negative feedback could
damage his professional reputation. Concerned about potential impact on his current
job and future career prospects, he made the difficult decision to shut down the original
repository.

## A community-driven revival

The original developer was planning to create a new anonymous account and republish the app with a
different package name, but before he could do so, the community had already adopted N7-X's fork as
the new "official" version of Raccoon.

Recognizing the community's decision, the original developer worked with the co-maintainer to
perform a comprehensive migration: changing the package name, updating URLs for remote assets, and
moving the project to a neutral organization account.

## Better than before

The transition proved beneficial for the project. The new team gradually restored and improved all
aspects of the app, including:

- enhanced Continuous Integration workflows;
- improved test coverage calculation;
- shared build logic among modules with Gradle convention plugins;
- better quality assurance with static analysis tools.

Today, Raccoon for Lemmy is in better condition than ever, with active community support and ongoing
development. While the circumstances that led to the transition were unfortunate, they ultimately
resulted in a stronger, more sustainable project structure.

The incident serves as a reminder of both the challenges facing open source maintainers and the
resilience of community-driven development when people come together to support valuable projects.

{% include tag_footer.html %}
