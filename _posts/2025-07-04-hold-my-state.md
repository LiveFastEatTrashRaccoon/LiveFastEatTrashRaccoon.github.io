---
layout: post
title: "Hold my state: why shared ViewModels are a life-changer"
date: 2025-07-04
tags: [ "kmp" ]
---

This week's technical post is going to deal with state holders and the major overhaul which Raccoon
apps have undergone [here](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/pull/953)
and [here](https://github.com/LiveFastEatTrashRaccoon/RaccoonForLemmy/pull/456) concerning how
state is retained and managed.

Before deep-diving in the technical questions and project history, let's start by making it
clear what domain we are in with some basic terminology, what is the issue we are trying to solve
and why this matters for app development in general and specifically for KMP.

## What is this all about?

We are dealing with the relationships between the UI — i.e. that part of the software which has the
responsibility of rendering graphical elements on the screen to convey some information to users and
collect user input — (generally called **«View»**), the business logic objects which represent the
entities being handled (called **«Model»**) and the presentational component which stands in between
the View and the Model and coordinates the interaction between them.

This intermediate component is called in different way depending on the architectural pattern
followed by developers, it can be called «Controller» in the Model View Controller (MVC)
architecture, «Presenter» in the Model View Presenter (MVP) one, «ViewModel» in
Model View ViewModel (MVVM), and so on. What is common to all of them is that this "middleman" is
responsible for:

- interacting with the View to present information;
- collecting user input from the View in order to perform changes in the Model.

How much and how each of these tasks is achieved and the relationships with the other two
elements of the triangle are different in MVC, MVP or MVVM, but this is outside the scope of this
article.

Concerning mobile development, on iOS the MVC pattern has been the standard for many years, it was
recommended one by Apple and it was an integral part of UIKit (the framework used to create UI on
iOS apps before the advent of SwiftUI). On Android, on the other side, the most widely accepted
pattern in modern apps is MVVM (coming built-in with AndroidX libraries and recommended by Google).

## What about Kotlin Multiplatform?

KMP apps can have either of these "share strategies":

1. The business logic (all or parts of it) are shared across platforms, which corresponds to having
   a shared Model but native Views;
2. All is shared (like we do in Raccoon apps) so Model, View and ViewModel are all shared (and the
   only native parts are the components dealing at the lowest level with hardware features such as
   the camera, Bluetooth, gallery, sharing data with other processes, playing videos, etc.).

While the first option leaves developers with the choice to share or not the ViewModel and, if they
decide to do so, handle the interaction on iOS with the native SwitfUI View (see, about this, the
[KMP-ObservableViewModel](https://github.com/rickclephas/KMP-ObservableViewModel) project which has
a brilliant solution for this issue), the second one requires to have a way to create components to
hold the screen state, observe it in the Compose UI and properly manage their lifecycle (e.g.
cancelling pending asynchronous operations when the portion of UI they are tied to goes off screen,
e.g. due to back navigation).

### The early stages: third party solutions

Initially, when working with Compose Multiplatform, there was no official solution for ViewModels
and the only available solution were third-party libraries
like [Voyager](https://github.com/adrielcafe/voyager). The latter, besides offering a comprehensive
navigation library, allows you to define not only your `Screen`s but also the `ScreenModel`s, bind
them so that their lifecycle is tied together, automatically managing their creation and disposal.

Going this way has a lot of positive aspects, such as:

- easy setup, especially if compared to other popular libraries in the KMP environment such
  as [Decompose](https://github.com/arkivanov/Decompose);
- pragmatic approach and thorough documentation (synthetic but extensive);
- it is well integrated with popular frameworks for dependency injection both native (Hilt) and
  multiplatform (Koin, Kodein) and reactive programming;
- the library works well and it does what it promises and many more things (animated transitions,
  etc.).

But, on the other hand, it has also several downsides:

- obtrusiveness: it forces you to follow their design choices (some of which are not
  Compose-idiomatic, for example screens are class instances and not functions);
- state restoration does not work with non-primitive constructor parameters (and even if screens
  are classes, you should resist at any cost the temptation of adding any instance variables,
  otherwise expect runtime crashes whenever the lifecycle leaves the `STARTED` state);
- no support for predictive back gesture on Android and weird way to intercept back navigation
  (using the `onBackPressed` callback of the root `Navigator` which acts globally and not on a
  per-screen basis, unlike the regular `BackHandler` Composable);
- deep link support: if sort of works even though with some workaround (which is crucial in Raccoon
  apps to support integration
  with [Mastodon Redirect](https://github.com/zacharee/MastodonRedirect)) but it feels fragile;
- the transition artifact works but does not play well in all scenarios (I've had multiple
  problems, e.g. with the Kodein + Compose combo);
- once you start using the library, you find yourself more and more tied to it because everything
  must be done in "their way", even at the cost of reduplicating existing components (such as using
  `BottomSheetNavigator` where Compose has `ModalBottomSheet`, etc.) which is needless and makes
  your project diverge more and more from mainstream.

### AndroidX ViewModels to the rescue

This situation changed drastically once for all in May 2024 when JetBrains's port of the popular
`org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose` library hit the stable distribution.
When you add this dependency, suddenly you have AndroidX's `ViewModel`s available with all
the same constructs every native Android developer is familiar with. For example, you can
configure their instantiation them with a `ViewModelProvider.Factory` and, if you add the
`org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-savedstate` library, you can also rely on
`SavedStateHandle` to be able to retain values even across death and recreation.

Here is a simple example of how you can setup a `ViewModelProvider.Factory` to retrieve instances
with Koin's `DI` and pass custom parameters with assisted injection

```kotlin
val VM_ARG_KEY = object : CreationExtras.Key<ViewModelCreationArgs> {}

class CustomViewModelFactory(private val injector: DI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val argument = extras[VM_ARG_KEY]
        if (argument != null) {
            val model by injector.instance<ViewModelCreationArgs, ViewModel>(
                tag = modelClass.simpleName,
                arg = argument,
            )
            return modelClass.cast(model)
        }

        val model by injector.instance<ViewModel>(tag = modelClass.simpleName)
        return modelClass.cast(model)
    }
}

@Composable
inline fun <reified T : ViewModel> getViewModel(arg: ViewModelCreationArgs? = null): T {
    val factory by localDI().instance<ViewModelProvider.Factory>()
    return viewModel(
        factory = factory,
        extras = MutableCreationExtras().apply {
            if (arg != null) {
                set(VM_ARG_KEY, arg)
            }
        },
    )
}
```

## Our experience at Procyon

Raccoon for Lemmy was born in 2023 with Voyager, because back then it was the best solution in terms
of tradeoff between functionality, flexibility and ease of use. I had tried other alternatives in
the past such as [Precompose](https://github.com/Tlaster/PreCompose)
or [Decompose](https://github.com/arkivanov/Decompose) but in all other solutions the disadvantages
outnumbered the pros.

When I started working on Raccoon for Friendica in 2024, even if AndroidX ViewModels were already
stable, I
chose to continue with Voyager because I was already familiar with it and my idea was to create a
proof-of-concept of a Friendica client as soon as possible so there was not room for
experimentation during project setup.

The situation changed in 2025, when I had grown more and more dissatisfied with Voyager and wanted
to give a try to a more standard solution,
considering [common ViewModel](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-viewmodel.html)
is the recommended solution by JetBrains for KMP apps.

I was stunningly surprised as how easy it was to migrate away from Voyager's `ScreenModel`s and even
get rid of the DI integration (which give me more flexibility if I decide to change in the future my
DI framework).

But there is more, actually: this was the first step to completely remove the Voyager
library (see [here](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/pull/954)
and [here](https://github.com/LiveFastEatTrashRaccoon/RaccoonForFriendica/pull/956)) and
migrate towards more standard solutions such as
[AndroidX navigation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation.html)
which again matches more closely JetBrain's recommendations.

One more time, being able to embrace change and being open minded towards technology evolution has
made me a better software architect and resulted in a more solid app for end users (since making
these changes was a great opportunity for refactoring and cleanup).

--- 

*What do you think of navigation libraries on Compose or Compose Multiplatform? Have you ever tried
Voyager, Decompose or Precompose and want to share your experience? Let us know!*  