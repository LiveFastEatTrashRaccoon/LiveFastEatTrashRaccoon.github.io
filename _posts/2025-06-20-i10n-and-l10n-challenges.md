---
layout: post
title: "Global localization: Building a truly international open source app"
date: 2025-06-20
tags: [ "l10n", "community", "procyon" ]
---

*How a humanities background shaped the technical decisions behind Raccoon's multilingual journey*

Let me start with an adaptation from the Raccoon for
Lemmy [manifesto](https://github.com/LiveFastEatTrashRaccoon/RaccoonForLemmy/blob/master/CONTRIBUTING.md):
our goal is to offer an experience where everyone can "feel at home." This simple phrase
encapsulates a fundamental principle—enabling users to interact with software in their native
language isn't just a nice-to-have feature, it's essential for true accessibility and inclusion.

As someone who studied both computer science and humanities, with extensive coursework in foreign
languages (English and Spanish), translation, and interpreting, I've always understood that
localization goes far beyond simply translating text. My master's thesis focused on
internationalization and open source localization tools, so when I began developing the Raccoon
apps, multilingual support wasn't an afterthought—it was baked into the foundation from day one.

## The philosophy: ownership through personalization

A crucial aspect of making users truly **own** their application is allowing it to adapt to them,
not the other way around. This means providing the most direct interaction possible, starting with
language preference. When someone opens an app and sees their native language, there's an immediate
sense of belonging—the software feels like it was made *for them*.

This philosophy guided every technical decision I made about localization infrastructure.

## Mapping our global expansion

### Phase 1: Building the foundation

I started by implementing support for the languages I could personally manage: English, Spanish, and
Italian. This gave me direct control over quality and helped establish our localization workflow
before opening it up to community contributions.

### Phase 2: Community-driven growth

The Raccoon apps were adopted almost immediately across Europe, North and South America. Following
true open source principles, I welcomed contributions from native speakers who wanted to help. The
first community-contributed languages were: Portuguese (both European `pt` and Brazilian `pt_BR`
variants, German and Spanish (community review and improvements).

### Phase 3: Expansion across continents

With a solid foundation in place, I proactively created base resources for additional European
languages, hoping to attract new users and contributors. This strategy worked beautifully, bringing
in contributions for: Finnish, French, Irish, Norwegian, Romanian, Polish, Ukrainian.

Our reach eventually extended beyond Europe's borders with the addition of Chinese (Traditional
Taiwan `zh_TW`, Hong Kong `zh_HK`, and Simplified `zh_CN`) and Tamil.

Each new language didn't just expand our user base—it brought new perspectives and cultural insights
that improved the app for everyone. For this reason, I decided to create a dedicated
«Acknowledgements» section in both apps and include all translators in the list of contributors, in
order to give them visibility and recognize the importance of their work. The apps wouldn't be the
same without it!

## The technical journey: finding the right tools

### Early days: third-party solutions (mid-2023)

When I launched the first Raccoon app in mid-2023, Compose Multiplatform lacked native localization
support. I initially used [Moko Resources](https://github.com/icerockdev/moko-resources), which
worked well for simple cases but became unwieldy as our project grew in complexity. Having every
module depend on resources created maintenance nightmares, especially with complex KSP (Kotlin
Symbol Processing) configurations.

### The Lyricist era: flexibility with tradeoffs

As Compose Multiplatform evolved and added built-in support for resources like drawables and fonts,
I migrated away from third-party dependencies, however, localization support was still missing in
early versions (e.g. 1.6.0, released in February 2024).

Enter [Lyricist](https://github.com/adrielcafe/lyricist), developed by the same team behind
the [Voyager](https://github.com/adrielcafe/voyager) navigation library I was already using.
Lyricist offered excellent flexibility but came with significant challenges:

**Option 1: XML processors**

- **Advantage** uses standard Android-style XMLs (converted to resource files);
- **Problem**: Using format specifiers (`%1$s`, `%1$d`, etc.) may lead to invalid generated code;
- **Major issue**: It completely broke reproducible builds, making F-Droid submissions painful.

**Option 2: Plain Kotlin files**

- **Advantage:** Full control over string resources;
- **Problem**: Not compatible with standard translation platforms
  like [Weblate](https://weblate.org/)-

### The collaboration challenge

Since these are community-driven projects, using professional translation platforms was essential. I
still remember receiving Spanish translation reviews via email—there had to be a better way!

When I set up our Weblate projects, I was still using Lyricist, which meant I had to write Python
scripts to convert between XML and Kotlin for every translation update. This manual process was
error-prone and time-consuming, especially when using the Weblate-GitHub integration to open PRs
with new translations, because I had to manually regenerate resources after every merge.

### The breakthrough: native Compose Multiplatform support

Finally, with version 1.6.10 (released in May 2024), Compose Multiplatform added native support for
reading string values from the `composeResources` directory. This was the turning point — I could
now use Android-style XML resources that seamlessly integrated with Weblate, eliminating conversion
scripts entirely.

## Lessons learned

**Community is everything**: native speakers don't just translate — they bring cultural context that
makes your app truly feel local. It is important to acknowledge their work and give them visibility!

**Tool selection matters**: the right technical choices can make the difference between a
maintainable internationalization system and a maintenance nightmare.

**Start with strong foundations and design for extension**: investing time in proper localization
infrastructure early pays dividends as your project scales. I is important to write future-proof
code and keep flexibility in mind because the ecosystem keeps evolving, and new solution may be
worth embracing.

**Platform integration is key**: using tools that integrate well with translation platforms like
Weblate dramatically reduces friction for contributors and allows them to focus on what really
matters.

## What's next

The localization journey continues. The foundation I've built makes adding new languages
straightforward, and our community-driven approach ensures quality while fostering a sense of shared
ownership.

If you're building a multilingual app, remember: localization isn't just about translating
strings — it's about creating an experience where every user feels the software was made
specifically for them. That's when you know you've truly succeeded in making your app feel like
home.

---

*Want to contribute to Raccoon's localization efforts? Check out
our Weblate projects ([here](https://hosted.weblate.org/engage/raccoonforlemmy)
and [here](https://hosted.weblate.org/engage/raccoonforfriendica)) or visit
our [GitHub page](https://github.com/LiveFastEatTrashRaccoon) to get started.*
