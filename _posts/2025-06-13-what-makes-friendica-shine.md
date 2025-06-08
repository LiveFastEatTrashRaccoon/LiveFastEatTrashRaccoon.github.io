---
layout: post
title: "Friendica: the Swiss Army knife of the Fediverse"
date: 2025-06-13
categories: [ friendica, social, activitypub ]
---

While most fediverse platforms force you to choose between microblogging, photo sharing, or link
aggregation, one platform refuses to make you pick just one.

[Friendica](https://friendi.ca/) is a social environment integrated in the Fediverse, just like
Mastodon or Pixelfed ‒ but it is also compatible with Bluesky, Tumblr, WordPress, GNU Social,
Diaspora, RSS and several more tools and platforms.

## Universal federation hub

Unlike other platform, which primarily focus on single types of contents (e.g statuses or photo
sharing), Friendica serves as a universal translator across multiple protocols, acting like a hub
which brings "together all of the Fediverse into one experience" (according
to [hankg](https://nequalsonelifestyle.com/2022/04/29/my-fediverse-decisions-crossroads-on-friendica/)).

## The Facebook the Fediverse

Friendica uses a **post/replies idiom** with a Facebook-like UX, rather than the Twitter-like status
streams found in Mastodon. This creates a more traditional social networking experience with
threaded conversations and a focus on community interaction.

Just like Facebook, Friendica features the concept of **groups** (which are treated as ActivityPub
actors), i.e. a special kind of profiles which act as an aggregator for posts related to
the same topic, similar to Lemmy's communities.

Other features which Friendica shares with Facebook are:

- the possibility to organize uploaded media into a **gallery** with different albums (and images
  inside each album);
- an integrated **event calendar** (with birthdays and custom events);
- **direct messages** between users;
- the ability to **quote** (cross-post) other people's posts, not just boosting them but embedding
  them into a new one;
- the possibility to organize contacts in **circles** (which can not only be used like user-defined
  timelines to read posts but also as a target scope to publish posts to).

## Enhanced content

Friendica offers robust content capabilities including **unlimited post length** (within server
limits), **extensive media support** for photos (even embedded), audios, videos, and file
attachments, plus geotagging options.

Moreover, posts can have, besides the main content, also a **title** and a spoiler, and their body
supports **rich formatting** with different text styles (italic, bold, strikethrough, monospaced,
quoted, itemized, etc.)

With this respect, Friendica positions itself as a
[Swiss Army knife](https://fedi.tips/friendica-a-flexible-fediverse-server-type-with-long-posts/)
‒ less specialized than Mastodon (microblogging) or Pixelfed (photo sharing), but more versatile
in connecting different communities and content types under one roof.

## External integrations

Friendica can import arbitrary websites and blogs into your social stream via **RSS/Atom feeds**,
making it act as both a social network and content aggregator. This feature is unique among
major fediverse platforms.

It also features an **email connector** which allows to you add conventional email contacts to your
social networking stream, making it possible to bidirectionally interact via email with the
configured contacts just as if they were participating in the social stream.

## The mobile challenge

This feature richness creates both Friendica's greatest strength and its biggest challenge when
implementing clients. As a matter of fact, the web interface is great to access all of these
features on desktop but on a mobile device there are different constraints for usability and
readability, so having an app to use the most important functions of the platform would be a great
plus.

Ideally, an app for Friendica should have at least the following features:

- timeline view with ability to switch feed type (public, local, subscriptions, user-made lists);
- post detail, i.e. opening a conversation in its context and see the replies, number of re-shares
  and people who added it to favorites;
- user detail with ability to see posts, post and replies, pinned posts and media, subscribe for
  notifications from a user, follow/send a request or unfollow them, see following/followers;
- support for ActivityPub groups, with the ability to open threads in "forum mode";
- see trending posts, hashtags, links and following recommendations;
- follow/unfollow an hashtag and view all the posts containing a given hashtag;
- post actions (re-share, favorite, bookmark) and – for own ones – edit, delete or pin to profile;
- global search hashtags, post and users containing some specific terms;
- customize the application appearance with color themes, font face and size, etc;
- login via OAuth2;
- view and edit one's own profile data;
- view incoming notifications and filter the list;
- manage one's own follow requests and accept/reject each one of them;
- view the list of one's own favorites, bookmarks and followed hashtags;
- create a post/reply with formatted text, image attachments (and alt text), spoiler and title;
- schedule a post (and change its schedule date) or save it to drafts;
- report posts/users to administrators for content moderation;
- mute/unmute, block/unblock users and manage the list of muted/blocked users;
- manage one's own circles (i.e. user-defined lists);
- multi-account with easy ability to switch between accounts;
- send direct messages to other users and see conversations;
- manage one's own photo gallery;
- view one's own event calendar.

---

*This is the point where
[Raccoon for Friendica](https://play.google.com/store/apps/details?id=com.livefast.eattrash.raccoonforfriendica)
comes in, trying to provide a mobile solution functional and aesthetically convenient at the same
time.*
