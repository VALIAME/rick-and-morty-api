# Rick and Morty API Application Architecture

This document explains the architecture of the Rick and Morty API application, a Kotlin Multiplatform project with a focus on the Android implementation.

## Architecture Overview

The application follows Clean Architecture principles with a clear separation of concerns:

- **UI Layer**: Compose UI components and ViewModels
- **Domain Layer**: Business logic and use cases
- **Data Layer**: Repositories, data sources, and models

## Project Structure

```
org.mathieu.cleanrmapi
├── CleanRmApiApplication.kt        # Application class for Android
├── ui
│   ├── App.kt                      # Main Compose UI entry point
│   ├── MainActivity.kt             # Android activity
│   ├── core
│   │   ├── composables             # Reusable UI components
│   │   ├── sound                   # Sound functionality
│   │   ├── theme                   # UI theme definitions
│   │   └── Destination.kt          # Navigation destinations
│   └── screens                     # UI screens organized by feature
│       ├── characters
│       ├── characterdetails
│       ├── episodedetails
│       └── locationdetails
├── domain                          # Business logic and use cases
├── data                            # Data repositories and sources
└── di                              # Dependency injection configuration
```

## Key Components

### UI Layer

- **Screens**: Implement individual features (character list, details, etc.)
- **ViewModels**: Handle UI state and business logic for each screen
- **States**: Immutable data classes representing UI state
- **Actions**: Events triggered by user interactions

### Navigation

Navigation is implemented using Jetpack Navigation Compose with defined routes in `Destination.kt`.

### Dependency Injection

Koin is used for dependency injection across the application. The configuration is initialized in `CleanRmApiApplication.kt`.

### Platform-Specific Implementations

The application uses Kotlin Multiplatform to share code while implementing platform-specific features:

- **Sound**: Platform-specific implementations for sound playback
  Platform-specific implementations:

  // Android implementation
  actual class SoundPlayerFactory actual constructor(): SoundPlayer {
  // Implementation details
  }

### Database

Room is used for local data persistence:
- Entities represent database tables
- DAOs handle database operations
- Room Database manages connections

## Multiplatform Support

While the project targets multiple platforms, platform-specific code is organized in dedicated source sets:
- `commonMain`: Shared code for all platforms
- `androidMain`: Android-specific implementations
- `desktopMain`: Desktop-specific implementations

## Build and Dependencies

The project uses Gradle with the Kotlin Multiplatform plugin. Key dependencies include:
- Jetpack Compose for UI
- Koin for dependency injection
- Ktor for network requests
- Room for database
- Kotlinx libraries for coroutines and serialization

## Best Practices

- **Code Organization**: Functionality is separated by feature and layer
- **Naming Conventions**: Classes are named according to their responsibilities
- **Comments**: Complex logic is commented for clarity
- **Managers**: Used for complex cross-cutting concerns

## Project Setup

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that's common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple's CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you're sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.