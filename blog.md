---
layout: page
title: Blog
---

# Blog

Stay updated with the latest news, development progress on the Raccoon apps, and thoughts about the
Fediverse.

<div class="posts">
  {% for post in site.posts %}
    <article class="post">
      <h2><a href="{{ post.url }}">{{ post.title }}</a></h2>
      <time datetime="{{ post.date | date_to_xmlschema }}">{{ post.date | date: "%B %d, %Y" }}</time>
      <p>{{ post.excerpt | strip_html | truncate: 200 }}</p>
      <a href="{{ post.url }}">Read more →</a>
    </article>
  {% endfor %}
</div>
