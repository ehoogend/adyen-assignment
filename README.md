# Nearby Places Android App

This Android application allows users to discover nearby places of interest using the Foursquare API.
It provides a list of places near the user's current location and displays detailed information about each place.

## Architecture

The application follows the **MVVM (Model-View-ViewModel)** architecture pattern with a **Repository pattern** for data access.

- **Model:** Represents the data of the application, such as `Place` objects.
- **View:** Represents the UI components built using Jetpack Compose.
- **ViewModel:** Acts as an intermediary between the View and the Model, providing data to the View and handling user interactions.
- **Repository:** Provides an abstraction layer for data access, hiding the underlying data sources from the ViewModel.

This architecture promotes separation of concerns, testability, and maintainability. The use of Jetpack Compose for the UI allows for a declarative and reactive approach to building user interfaces. Hilt is used for dependency injection, simplifying the management of dependencies.

**Location Handling and Permissions:**

The Nearby Places module demonstrates a robust and user-friendly approach to handling location data and permissions. 
The `LocationRepository` abstracts the complexities of location services and permissions, providing a clean interface for the ViewModel to access the user's location. 
The UI gracefully handles different permission scenarios, including requesting permissions, explaining the need for location access, and providing options for the user to grant or deny permissions. This ensures a smooth and transparent user experience while respecting user privacy. 
This approach aligns with the MVVM architecture by encapsulating location logic within the repository and allowing the ViewModel to focus on data presentation and user interactions.

**Error Handling and UI States:**

The Nearby Places module employs a comprehensive approach to error handling and UI state management. 
The `NearbyPlacesViewModel` utilizes sealed classes to represent different UI states (loading, success, error, empty, no permission), providing a clear and structured way to handle various scenarios. This ensures a smooth and informative user experience even in the presence of errors or data loading delays.
This approach is a key aspect of the MVVM architecture, as it allows the ViewModel to manage the UI state and communicate it effectively to the View.

## Project Structure

The project consists of two main modules:

### 1. App Module

This module contains the core functionality of the application, including the user interface, business logic, and data layer. It is structured using a layered architecture approach.

- **`api`:** Handles interaction with the Foursquare API.
  - **`Place`:** Data class representing a place of interest.
  - **`PlacesResponse`:** Data class for API responses.
  - **`PlacesService`:** Interface or class for making API calls.
- **`data`:** Contains the data layer, organized by feature.
  - **`places`:** Handles data related to places.
    - **`PlacesRepository`:** Fetches and caches place data.
    - **`PlacesRemoteDataSource`:** Fetches place data from the Foursquare API.
  - **`location`:**
    - **`LocationRepository`:** Obtains the user's current location.
  - **`remote`:** Handles communication with remote data sources.
    - **`RemotePlacesDataSource`:** Fetches place data from the Foursquare API.
- **`di`:** Contains Dependency Injection Modules (using Hilt).
- **`ui`:** Contains the user interface components, built using Jetpack Compose.
  - **`MainActivity`:** The main entry point of the application.
  - **`MainNavHost`:** Sets up the navigation graph for the application, defining routes and screens.
  - **`features`:** Organizes UI features into separate packages.
    - **`nearbyPlaces`:** UI logic for the nearby places screen.
      - **`NearbyPlacesScreen`:** Screen composable displaying the list of nearby places.
      - **`NearbyPlacesViewModel`:** ViewModel for the nearby places screen, manages place data.
    - **`nearbyPlaces/detail`:** UI logic for the place detail screen.
      - **`NearbyPlaceDetailScreen`:** Screen composable displaying detailed information about a selected place.
  - **`theme`:** Contains theme definitions and styles for the application.
    - **`Theme`:** Provides composable functions to apply and customize the app's theme.
    - **`Color`:** Defines color palettes and resources.
    - **`Typography`:** Defines font styles and text sizes.

## Other considerations

### Security Considerations

For the purpose of this assignment and to ensure a seamless setup experience, the Foursquare API key is currently hardcoded in the `build.gradle.kts` file. 
However, in a production environment, it is crucial to store sensitive API keys securely. 
This could be achieved by using environment variables, local properties files, or dedicated secret management systems like GitLab CI variables. This approach would protect the API key from unauthorized access and ensure the security of the application.

### Testing

The project includes unit tests for core functionalities and business logic. However, due to time constraints, UI tests and snapshot tests have not been implemented yet. 

Implementation of snapshot tests was in progress but not finished due to time constraints and compatibility issues with animated navigation transitions.
See branch feature/snapshot-testing or PR [#3](https://github.com/ehoogend/adyen-assignment/pull/3).

In future development, adding UI and snapshot tests would provide comprehensive test coverage and enhance the overall robustness of the application.

Assignment text below
----------------------------------------------------------------------------------------------------

# Adyen Android Assignment

This repository contains the coding challenge for candidates applying for a Senior Android role at Adyen.
The Git repository is present in the zip file that you received and you can use it to show us your development process.

This challenge consists of two unrelated parts:

## 1. Cash Register
Your first task is to implement a cash register. See the `cashregister` module.

Criteria:
- The `CashRegister` gets initialized with some `Change`.
- When performing a transaction, it either returns a `Change` object or fails with a `TransactionException`.
- The `CashRegister` keeps track of the `Change` that's in it.

Bonus points:
- The cash register returns the minimal amount of change (i.e. the minimal amount of coins / bills).

## 2. App
Your second task is to implement a small app using the Foursquare Places API. See the `app` module.

The app should show a list of venues around the user’s current location.
Decide yourself which venue details should be relevant to the user. You have full freedom on how to present data on screen.
We've already added some code to make it a bit easier for you, but you are free to change any part of it.
We are going to check your implementation for understanding Android specifics (like handling configuration changes), UX choices, and overall architecture.
You are free to add any feature or code you want, but we also value quality over quantity, so whatever you decide to do, try to show us your best.

### Setup
Add your Foursquare `API_KEY` to `build.gradle.kts` as BuildConfig variable.
Tip: You can verify your credentials with `src/test/java/com/adyen/android/assignment/PlacesUnitTest.kt`
