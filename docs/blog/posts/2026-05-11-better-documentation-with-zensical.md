---
title: "Better documentation with Zensical"
date: 2026-05-11
categories:
  - libs
tags:
  - libs
---

*Published on May 11, 2026*

If you have
visited [Raccoon's user manual](https://livefasteattrashraccoon.github.io/RaccoonForFriendica)
recently, you might have noticed a major shift. The layout is cleaner, navigation is intuitive,
there’s a native search box, and you can finally toggle between  light and dark themes.

But why the sudden change?

### Beyond GitHub Pages

It all started when I began exploring hosting alternatives like [Codeberg](https://codeberg.org).
As more open-source projects move toward independent platforms, I wanted to see how Raccoon’s
documentation would fare outside the GitHub ecosystem.

While GitHub Pages is highly integrated, Codeberg favors a "DIY approach." This forced me to
re-evaluate my site generator. Jekyll served us well, but it felt aged. I needed something
modern, fast, and specifically built for software documentation.

### Search for the right tool

I briefly considered [Hugo](https://gohugo.io), Jekyll's successor: it’s incredibly powerful, but it
felt like overkill for just documentation. 
Then I found out about [Material for MkDocs](https://github.com/squidfunk/mkdocs-material).
It was an "ah-ha" moment: beautiful defaults, easy configuration, and a professional look
right out of the box.

However, a warning in the build logs gave me a pause:

!!! danger "Warning from the Material for MkDocs team"
    MkDocs 2.0, the underlying framework of Material for MkDocs, will introduce
    backward-incompatible changes, including:

    *   **All plugins will stop working** – the plugin system has been removed
    *   **All theme overrides will break** – the theming system has been rewritten

Following the link in the error message I found
[this article](https://squidfunk.github.io/mkdocs-material/blog/2026/02/18/mkdocs-2.0/),
explaining that MkDocs 1.x is essentially unmaintained and 2.x is heading in a direction
incompatible with the philosophy of Material for MkDocs.

### Enter Zensical

That’s when I discovered [Zensical](https://zensical.org/).

Zensical isn't just another theme; it’s the successor to Material for MkDocs, built to solve the
fragmentation and maintenance issues of the MkDocs ecosystem. It takes everything I loved about
Material — the aesthetics, the ease of use, the rich feature set — and builds it on a more stable,
forward-looking foundation.

The migration was fairly easy, thanks to sane defaults and exhaustive documentation. Some of the
standout features include:

* **Custom palettes & Dark Mode**: effortless switching between light and dark themes;
* **Native search**: fast, client-side search that just works;
* **Organization**: powerful categorization with tags and multi-language support.

But what truly shines are the Markdown extensions. They allow for a rich documentation experience
with features like **admonitions**, **collapsible details**, **syntax highlighting**, **code tabs**,
**MathJax formulas**, and even **emojis**.

If you are managing a software project and want documentation that looks as good as your code,
Zensical is the way to go.

!!! question
    What about you? Have you found a documentation tool that changed the way you share your work?
