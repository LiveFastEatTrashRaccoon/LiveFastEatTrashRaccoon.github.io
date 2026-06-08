---
title: "Beyond contentDescription: Accessibility in Compose Multiplatform"
date: 2026-06-08
categories:
  - kmp
tags:
  - community
  - compose
  - ux
---

*Published on June 08, 2026*

## Accessibility in a nutshell

Let's start with a quick recap about definitions and general principles. a11y is the practice of
designing an environment so that in can be used equally by everyone, regardless of their
abilities.

Applied to digital platforms, this boils down to the **POUR principles**, i.e. the foundational
principles identified by the WCAG:

- **Perceivable:** information and UI components must be presented to users in a way
  they can perceive (e.g. images must have alternative text, clickable controls are apparent and
  their purpose is clear, etc.);
- :material-cog: **Operable:** components must be usable and functionalities can be accessed via
  different inputs (e.g. via touch input or voice commands) without barriers;
- :material-head-dots-horizontal: **Understandable:** the operation of the UI must be readable,
  predictable and designed in a way to help users avoid mistakes or guide users to correct them;
- :material-wrench: **Robust:** content must be reliable to be interpreted by a variety of assistive
  technologies and resilient to still be usable as they evolve over time.

## What Compose offers out-of-the-box

In an application like Raccoon, the simplest and most idiomatic way to create a UI is leveraging the
`org.jetbrains.compose.material3:material3` library which provides Compose Material 3 components for
CMP, i.e. the Material 3 design system by Google.

This has a lot of advantages, it provides a unified "language" to define User Interfaces which
follow well-established visual and behavioral patterns by design and from the ground-up. The library
is extremely flexible: it provides "ready-made" versions for common UI elements (with several
"extension slots" to adapt them to match the target look and feel). Moreover, since it is built upon
the`org.jetbrains.compose.foundation:foundation` library, when needed you have access to Compose
foundational layer to create the custom elements you need. The MD system also comes with a standard
library of symbols that have the role of a "visual dictionary" for icons to be used in front-ends.

With respect to a11y, Material 3 components and foundational components themselves have bult-in
support for accessibility: they are designed to be recognizable, usable, multi-input and
interoperable with assistive technologies (e.g. TalkBack on Android). Most of the time, they
work "out of the box" with minimal interventions needed.

Just to make an example, buttons already come with **semantics** that make them recognizable as
interactive components and their default layout has built-in elements (e.g. padding) which make them
follow the guidelines for human interaction. If a button's only content were an icon, nonetheless,
developers are encouraged to provide a description of the function of the button when the role of
the
symbol is not clear in its context.

!!! tip
    In general, whenever a graphic element (image or video) is inserted with no textual equivalent,
    developers need to insert a content description to meet the aforementioned **Perceivable**
    :material-eye: requirement.

More specific interventions highly depend on the particular domain the application belongs to.

## What we Actually Did

### UX for Screen Readers

Raccoon, for example, is client for a social network. This implies that the type of content which
is presented most frequently is the "feed": e.g. some kind of timeline with a sequence of posts,
the sequence of answers to a given post, the list of posts created by a given user, etc.

In the UI representing a timeline, each post in a timeline has multiple interactive elements
(avatar, author name, reply, reblog, favorite, bookmark, options). A screen reader user would have
to swipe a dozen times just to get past a single post, which makes scrolling through a timeline
cumbersome.

In order to overcome this issue, the main interventions have been:

- **Hiding Granularity:** use `Modifier.clearAndSetSemantics { }` on individual buttons in the
  footer and header, so that they are removed from the primary focus loop.
- **Merging Descendants:** apply `Modifier.semantics(mergeDescendants = true)` to the entire
  `TimelineItem` and `TimelineReplyItem`, so that the whole post is focused as one single unit.
- **Custom Actions:** re-implement the hidden buttons as `CustomAccessibilityActions` attached to
  the main post container.

As a result, users can navigate from post to post with a single swipe. If they want to
interact, they use the "Actions" gesture of their screen reader to select "Reply", "Favorite",
etc.

!!! danger
    A couple of caveats to avoid common pitfalls:
    
    - `mergeDescendants` can sometimes hide too much if not used carefully:
    - labels for `CustomAccessibilityActions` must be localized to remain truly accessible.

Key commits:
[`1aa17ea`](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/commit/1aa17ea){ target=_blank } 
and
[`0b7adf1`](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/commit/0b7adf1){ target=_blank }

### Content Parity

In a federated environment like Mastodon or Friendica, content often arrives as raw HTML. Embedded
images (inline **<img>** elements) within post text often had important `alt` descriptions. 

It is important that the value of this attribute is parsed (using
[Ksoup](https://github.com/MohamedRejeb/ksoup){ target=_blank } as a parser), passed up and used 
either as`contentDescription` for `Image` composables or as `alternateText` for inline contents
inside`BasicText` composables.

In this way, screen readers can now read the descriptions of images, both when they appear as media 
attachments and when they are embedded directly in the flow of a post.

On the other hand, nonetheless, too many `contentDescription` can also create too much noise: it is 
important to distinguish decorative VS functional items and annotate just the latter. So, 
as a part of the validation process, a systematic cleanup of alternate text properties was done.

Key commits: 
[`d2fc4dd`](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/commit/d2fc4dd){ target=_blank }

### Semantic Integrity

A `Switch` or `Checkbox` next to a `Text` composable often results in two separate focus points,
which is confusing and inefficient.

The solution to this is using `Modifier.toggleable` with `Role.Checkbox` or `Role.Switch` on the 
parent `Row` container, nullifying the `onCheckedChange` callback  to avoid double-handling.

By doing so, the entire row is treated as a single interactive control: when focused, the
screen reader announces the label and the state together.

Moreover, large lists like timelines or settings screens are hard to navigate if you can't jump to
specific sections.

The solution is to apply `Modifier.semantics { heading() }` to:
- post titles in the Timeline;
- headers in the Settings screens.

In this way, screen reader users can change their navigation mode to "Headings" and jump directly
from post to post or section to section, skipping the body text entirely if desired.

Key commits:
[`8ab2772`](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/commit/8ab2772){ target=_blank }

## Community is Key

The overall purpose of dealing with a11y is **inclusion**, which means that nobody is alone.

Tasks can be overwhelming to tackle all by oneself, but in an inclusive community you can always
rely on the support by others.

You may have noticed that the commits liked at the end of the previous paragraphs were not done by 
a single person. 

This is because for Raccoon all the interventions and fine-tuning summarized so far were 
implemented, tested, and validated by at least two people:
[pvagner](https://github.com/pvagner){ target=_blank } on the one side and the project maintainer
on the other.

## Why All This Matters

Ultimately, accessibility isn't a checklist or a set of technical hurdles: it is a commitment to our
users' dignity. By moving beyond simple content descriptions and thinking about the semantic flow
of our apps, we ensure that the Fediverse remains a place where the 'open' adjective in FOSS
applies to everyone.

And, for fellow developers, the next time you build a component, ask yourself: is this just visible,
or is it truly reachable?

--- 

*[a11y]: Accessibility
*[WCAG]: Web Content Accessibility Guidelines
*[CMP]: Compose Multiplatform
*[UI]: User Interface
*[UX]: User eXperience
*[MD]: Material Design
*[FOSS]: Free and Open Source Software