---
layout: home
title: Mission
---

# Building the future of social communication

Welcome to the **Procyon Project** — creating open source Kotlin Multiplatform applications that
make the Fediverse accessible to everyone. Our mission is to provide beautiful, user-friendly apps
that connect people through decentralized social networks.

We want to make it easier for everyone to find and access their favorite space in the Fediverse, be
it in discussion or social networking.

## Why choose Procyon apps?

- **Open source**: All our code is freely available and auditable
- **Privacy first**: No tracking, no ads, no data mining
- **Community driven**: Built by users, for users
- **Decentralized**: Support the federated web and digital freedom

## Latest Updates

{% for post in site.posts limit:3 %}

- [{{ post.title }}]({{ post.url }}) - {{ post.date | date: "%B %d, %Y" }}
  {% endfor %}

[View All Posts](blog.md){: .btn}

---

*Join the movement towards a more open, decentralized Internet. Download our apps and be part of the
change!*
