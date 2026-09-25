---
title: "The Intentional Raccoon: Why I Code the Hard Way"
date: 2026-07-20
categories:
  - community
  - procyon
tags:
  - community
  - procyon
---

Do you remember the
[reaction](2026-06-20-oss-sustainability.md#the-why-behind-the-trash){ target=_blank }
my admin friend got when they published a post about Raccoon's 1.0.0 release on `lemmy.world`?

Lemmy users will be Lemmy users: radical, aggressive, self-referential. I tried to work "for"
them in the past and create a client app, but for my IRL mental sanity I had to take several
[pauses](2025-06-17-what-happened-to-raccon-for-lemmy.md){ target=_blank } over time, and now I am
considering archiving that project altogether.[^1]

Let alone the attitude, let alone that the OP was accused of being a bot for using a human
expression like "finally", the concerns expressed are real and shared by more and more people.

## The Reverse Turing Test

As the Internet is being flooded by AI-generated content, some people are becoming paranoid about
"AI slop". It seems like they have nothing better to do than Turing-test everything to detect any
non-human footprint and, if they do, they start throwing stones, crying about techno-feudalism,
spread alarmism about electricity or water consumption, etc.

What made me most sad about this episode is that the person who was accusing didn't spend any time
trying to understand who he was talking to and what he was talking about. So, here is my attempt
to clarify who I am and what my personal position is on AI usage in OSS development, especially
as side-projects are concerned.

## A Choice of Freedom

I got a university degree in CS. I've been working in IT for fifteen years at several different
companies. I've been experiencing on my own skin what being into the "meat grinder" feels like, long
before 2022 when ChatGPT promised to solve all mankind's (and programmers') issues.

At work, I have to deal with unrealistic deadlines (not decided by me), maintain legacy code (
written by people who left the company), adhere to conventions or obey someone else's decisions (
I don't fully share).

Even *when*, *how much* and from *which provider* to use AI is not my decision from 9 to 6, when I
sit at my desk in the office.

Side projects have always been an "escape way" to me. When I come home (in evenings and weekends)
and open my IDE I have a _totally different_ approach. I am free to experiment, scout new
technologies *I* like, learn things *I* choose, set *my own* schedule and priorities without
checking the clock, write and organize code according to *my* personal taste. There's more, usually
I solve real problems I experience in *my* life in first person.

There is something deeply personal in that. It makes me feel the joy of creating something with my
commitment, week after week, like growing a tree. It makes me feel a sense of achievement whenever I
implement a feature request or fix a bug, which is greater the more difficult it was to get to the
solution, like in a videogame (quest - reward).[^2]

To be honest, developing "at my pace" also has a nostalgic bit to it: it reminds me of an era when
developing software still had some "craftsmanship" in it, when I was younger and the future still
seemed bright.

If I vibe-coded my apps, what would I get? I would give up on all creativity. The "videogame"
dynamic would almost be gone. I wouldn't learn anything which enriches me as a person and as a
professional.

## Assembly vs. Craft

Working in my 9-to-6 job has always felt like being in an assembly line. You clock in,
the machine starts grinding, you have to follow the rhythm, there's no questioning. Efficiency
(reduce time to save company's money), functionality (adhere to requirements someone else decided),
predictability (enforce standards and prioritize repeatability) are key.

But we all remember «Modern Times» by Chaplin, that is alienating. I am not saying that in my
Raccoon apps everything is handmade, I use several third-party libraries and I didn't reinvent the
wheel for networking, persistence, data (un)marshaling, UI, etc. But I still choose how all the
pieces fit together, like assembling an IKEA piece of furniture.

And, I will never insist too much on it, there's value in learning something the hard way, trying
things out, disassembling and reassembling elements to study the internals, is **not at all** a
waste of
time. Scientists and technicians, we need to experiment, observe and interpret our results to
progress. And the struggle is a great teacher in that: remove the struggle, impoverish the learning.

And resorting to shortcuts is self-inflicted damage too. AI may be wiping out the process of writing
code, especially at big scale in large companies. But the ability to understand the "why" behind
each line of code is not being superseded: you need it to evaluate and review AI generated code.

## Conclusion: Pro-Intentionality, not Anti-AI

The fact that Linus Torvalds recently said that AI is clearly a useful tool, that Linux is not an
anti-AI project, and that anyone with issues with that should fork it and go their way, has
generated a lot of debate (source
[here](https://lore.kernel.org/linux-media/CAHk-=wi4zC+Ze8e+p3tMv8TtG_80KzsZ1syL9anBtmEh5Z40vg@mail.gmail.com/){ target=_blank }).

Yes, that is true, especially in a project like the Linux kernel where efficiency and scale are
the only metrics. For industrial usage, AI makes perfectly sense from an economic point of view.

Nonetheless, the Procyon Project feels like a "workshop" to me (not an industrial "factory") and
there is space for human creativity.

Like an artisan pours a little bit of themselves in each one of their works, when side projects
are approached like a craft (and not a utility) something similar happens. And the "soul" emerges
in little weird details, e.g. some play on words in the
[theme names](2025-07-01-playful-themes.md){ target=_blank }.

LLMs aim for the "average" result based on their training data, human work aims for specificities.
To me, like to many other devs I guess, there are projects which feel like a "sacred space" for
manual craft.

!!! question

    Where do you draw the line between using AI for productivity and letting it take over the
    "fun" part of your development process?

[^1]: Or, rather, merging the Lemmy client into the Mastodon / Friendica one, since it is already
multi-instance.
[^2]: The more problems I solve, the more experience I gain, which makes me a better
professional in my 9-to-6 job, stay tuned for more in a future article.

*[IRL]: In Real Life
*[AI]: Artificial Intelligence
*[CS]: Computer Science
*[IT]: Information Technology
*[OP]: Original Poster
*[OSS]: Open Source Software
*[IDE]: Integrated Development Environment
*[LLM]: Large Language Model
*[UI]: User Interface