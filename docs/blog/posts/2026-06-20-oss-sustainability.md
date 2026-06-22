---
title: "The Raccoon's Dilemma: Balancing Features, Maintenance, and Sanity"
date: 2026-06-20
tags:
  - community
  - procyon
---

*Published on June 20, 2026*

The idea of writing about the balance between features and maintenance in our apps has been
lingering in my mind for over a year. But it never *felt like* the right moment, so I kept
putting it off… ironically, somehow like the maintenance tasks I'm writing about.

This procrastination alone says a lot about my way of managing open-source side projects. It's a
delicate dance between personal life and professional obligations. Days only have 24 hours,
weeks only have 7 days (trust me, I've checked)!

Between family, a full-time job, and trying to be a functional adult in these chaotic times,
maintaining apps can feel like a secondary full-time job, just without any paycheck.

One might wonder: *Why on earth do I do this?* It's a fair question. In today's article, I want to
pull back the curtain on why Raccoon exists and why our development roadmap looks the way it does.

## Longing for the Wild

I've worked as a developer for more years than I'd like to admit (let's say my early code
belongs in a museum, or perhaps a dumpster). Throughout that time, I often struggled to find room to
experiment. As a junior, every choice was made for me. As I moved into mid and senior roles, I was
often tethered to legacy systems or constrained by "steering committees" which is often
corporate-speak for «we don't like change».

I don't blame them: companies avoid risk because a bad tech choice can haunt a team for years. So, I
was usually left playing in the safe corners of projects.

Even while bouncing between backend, web, iOS, and Android, something was missing. I missed the
thrill of building from the ground up. I wanted the freedom to choose a foundation, evaluate it in
the real world, and pull a complete U-turn on failure without causing a financial crisis for my
employer.

## The Perfect Storm

In 2023 the stars aligned. Kotlin Multiplatform[^1] transitioned from Beta to Stable(and 
Compose Multiplatform was in Alpha for iOS and stable elsewhere).

Meanwhile, the "Reddit API Apocalypse" killed off third-party clients, driving a massive migration
toward the "threadiverse" (Lemmy, Kbin, Sublinks, Friendica, etc.).

On a personal note, in early 2023 I had finally switched to a job that actually respected my 
evenings and weekends, no more unpaid overtime! :tada: :raccoon: :tada:

These factors converged into a single goal: build a Lemmy client for Android and iOS using KMP. A
year later, the Friendica client followed.

This was my playground. I could try libraries, break things, and discard what didn't work for my use
cases (like [Koin](2025-06-27-koin-vs-kodein-for-kmp-di.md)
or [Ktorfit](2025-07-09-ktorfit.md)).

So, finally, I had:

- **total tech freedom:** I was the lead, the junior, and the steering committee.
- **continuous learning:** hands-on experience with the bleeding edge.
- **purpose:** contributing something useful to an ecosystem I actually believed in.

## The "Why" Behind the Trash

Even without a paycheck, the ROI on these projects is massive. It's professional growth and
psychological satisfaction rolled into one. It's a process of hypothesis and experimentation, not
unlike academic research, but with more raccoons and fewer lab coats.

This also explains why instead of always going on and pile new features one on top of the other,
sometimes the development process seems to go in circles: e.g. switching from a network library to
another, rewrite the app navigation system, migrate localization from a framework to another, etc.

But why talk about this right now? 

Recently, I hit a milestone: version 1.0.0 of the Mastodon/Friendica client. A local admin wrote
an [announcement](https://poliverso.org/display/0477a01e-406a-2d2a-65e9-632377012051)
about it (in Italian). Then they ran it through machine translation and posted
an [English version](https://poliverso.org/display/788257c4-548493f919a881df-d1a3180d).

You know, the Fediverse is *federated*, so this propagated to Lemmy, where naturally critics
arrived:

!!! quote
    Why are people upvoting this? If OP used AI to 'make' this post, obviously the app is gonna be
    slop too.

The "proofs"? The word "finally" appeared in the middle of a list. Apparently, to some, using
transition words is a sign of a robotic takeover rather than, you know, being relieved that a
some longed-for feature is available to end users.

## The Human (and Raccoon) Element

This brings us back to sustainability. When we maintain open-source projects, we aren't just pushing
code; we're managing a community and navigating the weirdness of the Internet.

The "AI slop" accusation stung, but it also highlighted the irony: I do this work specifically to
keep the human element alive in tech. I build these apps so we have independent, non-corporate ways
to communicate and I do so because I love software development and learning new technologies.

So, to the "lemming" who thought I was a bot: I'm not. I'm just a developer who likes KMP, hates
unnecessary API fees, and occasionally enjoys a piece of "trash" code that eventually may turn
into treasure.

[^1]: At that time it was still called Kotlin Multiplatform Mobile (KMM) for those who remember.

*[KMP]: Kotlin Multiplatform
*[ROI]: Return on Investment
*[API]: Application Programming Interface
