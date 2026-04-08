# Pokedex Android App

A modern, clean, and robust Pokedex application built with Jetpack Compose, following Clean Architecture and MVI principles. This application allows users to explore Pokemon, view details, and manage their profile with local caching for offline support.

## 🚀 Features
- **Pokemon Explorer**: Browse a paginated list of Pokemon with a maximum of 10 items (as per current configuration).
- **Search**: Quickly find your favorite Pokemon by name.
- **Detailed View**: View comprehensive statistics, types, and high-quality images of each Pokemon.
- **Offline Support**: Powered by Couchbase Lite for seamless offline browsing after the first load.
- **Authentication**: Simple login/register system to manage your personal profile.
- **Modern UI**: Fully built with Jetpack Compose, featuring smooth animations and shimmer loading states.

## 📸 Preview
| Login Screen | Pokemon List | Pokemon Detail | Profile Screen |
| :---: | :---: | :---: | :---: |
| ![Login](path_to_screenshot) | ![List](path_to_screenshot) | ![Detail](path_to_screenshot) | ![Profile](path_to_screenshot) |

## 🛠 Tech Stack & Libraries
- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture**: Clean Architecture + MVI (Model-View-Intent)
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- **Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
- **Local Database**: [Couchbase Lite Android](https://www.couchbase.com/products/lite)
- **Paging**: [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data)
- **Navigation**: [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- **Logging**: [Timber](https://github.com/JakeWharton/timber)

## 🏗 Project Structure
The project follows a modularized approach to ensure scalability and maintainability:
- `:app`: The entry point of the application.
- `:core`: Shared components, base classes, utilities, and UI theme.
- `:api`: Data layer modules (Auth, Pokemon) containing Repositories, Data Sources, and Use Cases.
- `:feature`: Feature-specific modules (Auth, Pokemon, Profile, Splash) containing UI and ViewModels.
- `:buildlogic`: Custom Gradle conventions for consistent build configurations.

## 🚦 Getting Started
1. Clone this repository.
2. Open the project in Android Studio (Ladybug or newer recommended).
3. Sync Gradle and run the `:app` module.

---
Developed as a technical test showcasing modern Android development practices at code.id
