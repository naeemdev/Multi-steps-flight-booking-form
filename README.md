Multi-Step Flight Booking

Overview
This project is a multi-step flight booking form that enables users to provide their passenger,
passport, and contact information. It also includes advanced state management,
input validation, error handling, and is built using modern Android tools and architecture
principles.

📌 Features
Key Features Implemented

✅ Passenger Information
✅ Passport Information
✅ Contact Information
✅ Build Flavors
✅ Inputs validation: Full Name, Gender (dropdown), Date of Birth,Nationality,Email, Phone Number

🏗️ Tech Stack

* Kotlin – Primary programming language.
* Jetpack Compose – Modern Android UI toolkit for building native UI.
* Hilt – Dependency Injection (DI) framework.
* Retrofit – Networking and API integration.
* OkHttp – HTTP client.
* Material 3 – UI Components for modern design.
* Coroutines – For asynchronous programming.
* The project adheres to SOLID principles, follows Clean Architecture, and uses Jetpack Compose best
  practices for building Android apps.

📂 Project Structure
com.naeemdev.multistepsflightbookingform

│
├── data
│ ├── mapper # Maps API models to domain models
│ ├── remote # Network layer (Retrofit API Service)
│ ├── local # Local storage (Room Database)
│ ├── repositories # Repository implementations
│ └── models # Data models
│
├── di
│ ├── AppModule.kt # Provides global dependencies
│ ├── RepositoryModule.kt # Provides repository dependencies
│ ├── DatabaseModule.kt # Provides DatabaseModule dependencies
│ ├── DispatchersModule.kt # Provides DispatchersModule dependencies
│ └── AppEnvironmentModule.kt # Provides AppEnvironmentModule dependencies
│
├── domain
│ ├── model # Domain models
│ ├── repositories # Repository interfaces
│ ├── usecases # Business logic (Use Cases)
│ └── errors # Error handling classes
│
├── presentation
│ ├── components # Reusable UI components
│ ├── screens # Screens for Booking and Success
│
├── ui.theme # Theming and styling
├── MainActivity.kt # Entry point of the app
└── MyApp.kt # Hilt application class

🔗 API Integration
The app integrates with the GitHub REST API to fetch data:

Base URL: https://6790d6e6af8442fd7377f52d.mockapi.io/passportFormats.

📱 User Interface

Passenger Information
The first step of the flight booking form asks for the passenger's personal information:

Full Name: A text input for the user's full name.

Gender: A dropdown for selecting the gender.

Date of Birth: A date picker for selecting the user's birthdate.

Nationality: A dropdown for selecting the user's nationality.
(Note: now just showing the list of nationalities in dropdown but we can enhance to get list our
countries from API)

Passport Information
The second step asks for the user's passport information:

Passport Number: A text input for the passport number.

Username: A text input for the username associated with the passport.

Passport Expiry Date: A date picker for selecting the passport expiry date.

Contact Information
The final step requires the user to provide contact information:

Email: A text input for the user's email address.

Phone Number: A text input for the user's phone number

Validation

Full Name: Ensures that the user provides a valid name.

Gender: Checks if the gender is selected.

Date of Birth: Ensures the date of birth is in a valid format.

Nationality: Verifies that the nationality is selected.

Email: Ensures that the email address follows a valid format.

Phone Number: Verifies that the phone number is in a valid format.

Error Handling

*     Displays appropriate error messages for network issues, API failures, and empty data.

🚀 How to Run the App
1️⃣ Download the code
2️⃣ Open in Android Studio : Sync Gradle and install dependencies
Open the project in  **Android Studio Meerkat Feature Drop | 2024.3.2** or newer.
Ensure you have an Android Emulator or a Physical Device connected.
3️⃣ Run the App
`./gradlew build && ./gradlew installDebug`

Or, simply press Run ▶️ in Android Studio.

🎯
🧪 Unit Testing
The app includes unit tests for:

*     Use cases
*     Repository layer
*     Mappers
*     view model

Testing Tools:

JUnit for testing.

Mockk for mocking dependencies.

To run the tests, use the following command:
./gradlew test