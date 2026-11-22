---
title: "The importance of reproducible builds in FOSS"
date: 2026-11-22
categories:
  - dev
  - foss
tags:
  - foss
---

## Beyond "It Builds on My Machine"

Developing an open source application implies more than just sharing the code. It is true, on the
one hand, that sharing the code is a necessary condition so that other people (and fellow
developers) can inspect the code, build it on their own, and install it wherever they want.

For a project to be successful it also needs users, though. And non-technical people do not want to
set up their system in order to compile from source. This is why I also ship compiled binaries and,
in the case of mobile apps, installable packages.

In this scenario, it is vital to guarantee to users that the binary they install on their
device is exactly *the same one* they would get if they built it directly from the published source
code.

It is a matter of transparency: the distributed executable must not contain proprietary binary blobs
or dependencies that may include so-called "anti-features" (such as trackers, ads, or unwanted
telemetry).

The simplest way to guarantee this is to certify that the build is reproducible: building from
source in a neutral environment (outside the developer's direct control) **must** produce the exact
same executable distributed by the maintainer, such as the release packages built by CI.

In Raccoon, there are actually four distribution channels:

1. **Google Play Store**: the recommended way for regular users, since app bundles are
   analyzed by Google's inspection tools to make sure they comply with current industry quality and
   security standards;
2. **GitHub**: APKs are attached to releases on GitHub as the result of a CI pipeline step; they
   can be installed directly or via third-party utilities (e.g.
  [Obtainium](https://obtainium.imranr.dev/){ target=_blank }) and serve as a reference for
   reproducibility comparisons;
3. **IzzyOnDroid**: where users can install the latest versions almost immediately, since new
   versions often arrive earlier on Izzy's repo than on the production channel of the Play
   Store where review may take more time;
4. **F-Droid**: where releases usually arrive later due to custom extra checks, which include
   verifying build reproducibility.

For case 1, I create signed AABs directly on my machine and upload them to Google Play; for case 2,
APKs are created by the CI pipeline and signed with the same keystore I use for Google Play.
In case 3, IzzyOnDroid basically mirrors the artifacts from case 2, so there is not much extra work,
except for some caveats to reduce install size.

In case 4, however, the story is very different—and that is the topic of this week's deep dive.

## A One-Year Gap

Until last week (November 2026), there was a huge gap between the versions available on Google Play
Store, GitHub releases, or IzzyOnDroid and what was available on F-Droid. On the former three
channels, users could install 1.x, but F-Droid was still stuck on 0.4.2 (released in March 2025). 
Why did this happen?

First of all, 2026 has been quite a year for Raccoon. The recommended Gradle version for KMP
projects transitioned from 8.x to 9.x, and the Android Gradle Plugin 9.x series brought a major
overhaul of the project structure. Modules were renamed and added, Gradle convention plugins were
rewritten, build scripts were refactored, and build configuration was updated (see
[here](2026-05-17-dealing-with-kmp-shifting-sands.md){ target = _blank } for more).

As a result, the build instructions for the project changed and the F-Droid pipeline, which
neither I nor the app store maintainers were paying close attention to, inevitably fell behind.

## What happened?

### F-Droid Metadata Registry

In order to understand what happened, we need to look at how apps in F-Droid's catalog are
processed and updated.

F-Droid requires specific "recipes" on how to manage and build each app. These recipes are stored
as YAML in the `fdroiddata` repository. Each recipe contains several sections:

- project metadata (categories, license, homepage, authors, etc.);
- a registry of individual builds (each with specific build commands and environment settings);
- rules describing how to automatically update the recipe itself (e.g. when a new version is
  tagged).

### When Automatic Updates Break

The first thing that broke due to my refactoring was that automated update check. When F-Droid's
server ran `checkupdates`, it watched for a trigger event (e.g. a new git tag pushed to the
GitHub repository) and tried to extract the version code / name and generate a new build block.

However, due to my refactoring, the values used for `versionName` and `versionCode`  were moved 
from `gradle/libs.versions.toml` to the root `gradle.properties`. While this wasn't strictly 
required by the AGP migration, I decided to reserve the version catalog solely for third-party
dependencies, relying on project properties for internal versioning.

By doing so, however, F-Droid's version scanning by the `checkupdates` bot had been inadvertently
made ineffective.

Pointing F-Droid to the new location by setting a custom regex for the `UpdateCheckData` property
restored update detection, but that was not enough to solve the entire issue.

The 1.x build could not be compiled using previous configuration. For instance, the subproject
name for building the Android app had changed from `composeApp` to `androidApp` as part of the
AGP migration. 

But even after that was resolved, the build still failed during reproducibility checks.

### Conflicting Requirements

After digging deeper, I discovered two additional underlying causes.

Starting in February 2027, Google is enforcing stricter requirements for app optimization in order
to reduce install sizes and improve user experience. In preparation for these upcoming requirements,
I had recently enabled code minification and resource shrinking.

IzzyOnDroid, on the other hand, prefers to avoid "universal" APKs that bundle native shared
libraries for multiple architectures (e.g. x86_64 _versus_ arm64) into a single large package.
Instead, they prefer smaller per-architecture packages, achieved by enabling ABI splits in
the build setup.

This is a legitimate distributor preference that solves the same issue Google addresses with
the AAB format in a different way. On the downside, this configuration conflicts with
some optimization and reproducibility settings.

Therefore, build scripts and GitHub workflow definitions had to be adjusted so that:

- **when building locally:** optimizations are enabled, but ABI splits are disabled;
- **when building in CI:** optimizations are disabled, and ABI splits are enabled.

F-Droid introduced yet another constraint:

- obfuscation and minification are discouraged to preserve source auditability;
- ABI splits are not used, as F-Droid requires a single universal APK for its verification checks.

I updated the recipe to satisfy both constraints by pre-processing the build scripts. This time,
the build succeeded, but the reproducibility check failed.

Inspecting the F-Droid pipeline error logs revealed that resource shrinking was the culprit:
shrinking was still active in F-Droid's build environment while disabled in my GitHub reference
APK, leading to a binary discrepancy. 

Disabling resource shrinking in the F-Droid build instructions finally brought the generated
APK in line with the GitHub reference.

## Takeaways

What can be learned from all of this? First and foremost, different distribution channels embody
different philosophies:

- **Google Play Store** prioritizes runtime and download optimization: R8 code minification and
  resource shrinking are enabled to minimize download size. Quality checks are centralized on
  Google's servers, and developers are held accountable directly to their users and Google Play
  policies.
- **IzzyOnDroid** prioritizes release speed and lean packages: their recommended strategy is to use
  ABI splits so devices only download native libraries for their specific CPU architecture.
- **F-Droid** prioritizes public auditability: obfuscation and code / resource shrinking are
  discouraged or restricted for inspectability and to prevent non-deterministic transformations.

As a result, build workflows must be carefully designed to accommodate all these requirements with
minimal friction. In my case, this was achieved through a combination of Gradle compilation flags
(such as passing `-PciBuild=true` on GitHub Actions) and pre-processing in `fdroiddata` to
align those flags for what F-Droid needs.

Maintaining compatibility across multiple app stores requires ongoing effort. This extends beyond
build configuration; in the past, library architectural choices have triggered similar headaches,
such as the
[F-Droid catastrophe](2025-06-27-koin-vs-kodein-for-kmp-di.md#the-f-droid-catastrophe){ target = _blank }
we discussed last year.

For my future self, I will remember to treat reproducibility as an integral part of "project
hygiene", ensuring F-Droid users receive timely updates.

!!! question

    Are you interested in this topic and would like a more detailed breakdown of the exact checks
    F-Droid performs and how to test them locally before discovering build breakages?
    
    Let me know by commenting on the post linked to this article on
    [Procyon Project News](https://poliverso.org/profile/procyonprojectnews){ target = _blank }
    from your favorite Fediverse instance.

*[FOSS]: Free and Open Source Software
*[CI]: Continuous Integration
*[KMP]: Kotlin Multiplatform
*[AGP]: Android Gradle Plugin
*[AAB]: Android App Bundle
*[APK]: Android Package
*[ABI]: Application Binary Interface
*[UX]: User eXperience
*[DEX]: Dalvik Executable
*[CLI]: Command Line Interface