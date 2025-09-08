# Unsplash Image Search App

An Android app built with Jetpack Compose that allows users to search and browse images from Unsplash with pagination support.

## Prerequisites

- Android Studio Flamingo or newer
- Unsplash API account

## Instructions

1. Clone the repository:
   git clone https://github.com/RiccardoCestaro/ImageBrowser.git

2. Open the project in Android Studio.

3. Add your Unsplash API token inside the build.gradle.kts:
   buildConfigField("String", "ACCESS_KEY", "\"*here*\"")

## Build and Run

- Sync Gradle
- Run the app on an emulator or physical device

## Libraries used

- Jetpack Compose (UI)
- Coil (image loading)
- Retrofit + OkHttp (networking)
- Dagger-hilt

## Architectural Decisions

- MVVM architecture with ViewModel managing UI state
- Repository pattern to handle network requests
- Suspend functions for one-shot API calls instead of Flow, because each search request is independent
- LazyColumn with pagination detected via visible items
- AsyncImage from Coil for image loading; placeholders used for previews

