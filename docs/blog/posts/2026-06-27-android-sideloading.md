---
title: "Is this the end of sideloading on Android?"
date: 2026-06-27
tags:
  - community
---

*Published on June 27, 2026*

If you've been following the news from Google I/O 2026, you probably felt a bit of a chill down
your spine when the "Android Developer Verification" initiative was announced. For those who were
too busy debugging like me, the news is: starting **September 30, 2026**, Android will
change how it handles apps installed outside Play Store.

TL;DR: Every single Android app, regardless of how it's distributed, must be linked to a
**verified developer identity**. If an app is not registered by a verified dev, users will have to
go through a new "Advanced Sideloading Flow": this includes a mandatory 24 hour "cooling-off"
period, biometric checks, and a warning that you might be getting scammed (see the official
[Android Developers Blog](https://android-developers.googleblog.com/2026/03/android-developer-verification.html){ target = blank }
for more information).

## The "Safety" Argument

Google's reasoning for this change is, as expected, centered on security and fraud prevention.
The goal is to eliminate the "shadow economy" of malicious APKs used in social engineering scams.
Requiring a traceable identity for every piece of code that runs on a certified device, makes it
significantly harder for scammers to operate anonymously.

It's basically a KYC approach but targeting developers instead of end users: "Know Your Developers".
If a malicious app is found, there's a real person or entity to hold accountable or at least, a
verified ID that can be revoked across the entire ecosystem instantly.

## Divided Reactions

Reactions from the community was as polarized, as one may expect.

**The "Pro" Camp:**

Security researchers and enterprise IT managers have largely welcomed the move. For them, the "Wild
West" of Android sideloading has always been a liability. Reducing the risk of a "non-techy"
relative accidentally installing a banking Trojan via a WhatsApp link is seen as a huge win for the
average consumer.

**The "Against" Camp:**

On the other side, privacy advocates are rightly concerned about the requirement for
government-issued ID just to share a hobby project. The F-Droid community and the "Right to Repair"
crowd see this as a slow-motion execution of the very thing that made Android great: its openness.

E.g. The 24-hour wait period for unverified apps may be seen as "friction by design"; intended to
frustrate users into staying within the "walled garden" of the Play Store.

## The Raccoon's Two Cents

As someone who spends a lot of time in the "trash" (aka open-source side projects), I find myself
stuck somewhere in the middle.

On one hand, **publishing on F-Droid has always been a painful experience** for developers. Between
the strict requirements for reproducible builds and the manual controls involved, every update feels
like rolling a die. You never really know *when* (or even *if*) your latest fix will actually reach
your users. In a world where F-Droid already struggles with distribution lag, adding a 24-hour
OS-level delay on top of it feels like a kick while you're down.

On the other hand, wearing my professional developer hat, I have to admit that **quality and
requirement verification is more important than ever**. The barrier to creating mobile apps has
significantly lowered in the AI era which means the market is flooded with low-effort, unsafe, or
potentially deceptive software. Some level of accountability is necessary, and Google had already 
started going down that path,[^1] if (like me) you choose to distribute your apps through the Play
Store, you have to follow the rules, no exception for anyone.

## Closing thoughts

Is this the end of sideloading? Not quite (for now). But the days of the "one-tap APK install" are
clearly numbered. We're moving toward a future where freedom means more responsibility and awareness
of the consequences of your actions.

I'm curious to see how the Fediverse reacts once the rollout hits the pilot countries in September.
Until then, I'll be over here, double-checking my developer identity and hoping my reproducible 
builds actually… well, reproduce.

!!! question
    What do you think? Is the security tradeoff worth the loss of friction-less freedom?

By the way, as of now, the version of Raccoon for Mastodon / Friendica on F-Droid is 0.4.2, despite
having released 1.0.0 twenty days ago. After the hard work of making a new release available, it is
frustrating to see that users are prevented from installing it (and operating system restrictions 
are not enforced yet)!

[^1]: E.g. In November 2023 they introduced a policy that requires developers with newly created
personal accounts to conduct closed testing with at least 20 testers for 14 days before applying for
production access.

*[KYC]: Know your customer
*[IT]: Information Technology
*[APK]: Android Package Kit
