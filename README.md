# MapLibreApp: MVVM + Clean Architecture 
A responsive Android app written in Kotlin and Jetpack Compose for UI, integrating the MapLibre SDK,
and following MVVM and Clean Architecture principles.

The app also utilizes Kotlin Coroutines and Flow for asynchronous tasks, Ktor for API calls, and Dagger/Hilt
for dependency injections, and implements appropriate error handling.

The app provides two major functionalities:
* An interactive map with options to fetch and center on the user's updated location, indicated
by a pulsing dot, and with multiple toggeable terrain styles using a map style selector.
* A user profile screen, where the user can enter and edit their details, add a profile picture
from their gallery, select their birthday date from a calendar dialog, and save changes (through
a mock API call)

&nbsp;

## Demo Videos

Centering map on user's updated location:

https://github.com/MHamzaSajjad/MapLibreApp-MVVM/assets/54877353/39180579-c2bc-4bbf-8734-8f1ea7f7c98a

Switching map terrains using map style toggle button:

https://github.com/MHamzaSajjad/MapLibreApp-MVVM/assets/54877353/a45e4d5f-51e0-45a3-9dc7-a2d59ab473a8

User profile flow:

https://github.com/MHamzaSajjad/MapLibreApp-MVVM/assets/54877353/495b34d3-74ef-4a17-a386-9d8cb0e89bee


&nbsp;


&nbsp;

### High level architecture

* Kotlin
* MVVM and Clean Architecture
* Jetpack Compose, with a Single Activity 
* Kotlin Coroutines and Flow
* Dependency Injection using Dagger/Hilt
* Material 3 Design Component
* Gradle in Kotlin DSL

### Major libraries used

* [Jetpack Compose](https://developer.android.com/jetpack/androidx/releases/compose) - Modern
  toolkit for building native UI
* [Jetpack Navigation for Compose](https://developer.android.com/jetpack/androidx/releases/navigation#navigation-compose) -
  Navigation library for Jetpack Compose applications
* [Jetpack Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) -
  Lifecycle-aware components, including ViewModel support for Jetpack Compose
* [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Asynchronous programming
  with Coroutines
* [Kotlin Flow](https://kotlinlang.org/docs/flow.html) - Reactive streams for Kotlin
* [Ktor](https://ktor.io) - HTTP client for Android built using Kotlin and Coroutines
* [Coil](https://coil-kt.github.io/coil/) - Image loading library for Android, leveraging Kotlin
  Coroutines
* [Dagger Hilt](https://dagger.dev/hilt/) - Dependency injection framework

## Requirements

* Android Studio Giraffe (or higher) | 2022.3.1
* Android device or emulator running Android 7.1+ (API 25)

## Building and Installing the App

Clone and open the GitHub project in Android Studio. Sync Gradle files, build the project
and click run to open up the app in the emulator (or in an attached Android device).
