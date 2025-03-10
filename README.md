# Quiz App

A modern Android quiz application built with Jetpack Compose and following MVI architecture. The app fetches questions from the Open Trivia Database (OpenTDB) API and presents them in an interactive and engaging way.

> 🚀 **Built with [Cursor AI](https://cursor.sh/) in just 1 hour!**

## 📱 Download APK

You can download the latest APK from the [output/QuizApp.apk](output/quiz-app.apk) file.

## 🎥 Demo

Check out the app in action:

https://github.com/user-attachments/assets/c7549bc0-660d-4bef-b1fd-580d5fbe4f0b

## Features

- 🎨 Material 3 Design with dynamic theming
- 📱 Fully responsive UI with animations
- 🌐 Integration with OpenTDB API
- 📊 Real-time progress tracking
- 🎯 Multiple categories
- 🔄 Clean MVI Architecture
- 🚀 Kotlin Coroutines & Flow
- 🌓 Light/Dark theme support

## Tech Stack

- **UI Framework**: Jetpack Compose
- **Architecture**: MVI (Model-View-Intent)
- **Networking**: Ktor Client
- **Concurrency**: Kotlin Coroutines & Flow
- **Navigation**: Jetpack Navigation Compose
- **API**: Open Trivia Database (OpenTDB)
- **Development**: Built using Cursor AI


### Components

- **Events**: User actions and UI events
- **State**: Single source of truth for UI state
- **Effects**: Side effects like navigation and toasts
- **ViewModel**: Handles business logic and state management

## Screens

1. **Get Started Screen**
   - Welcome message
   - Theme selection
   - Start quiz button

2. **Category Selection**
   - Grid of quiz categories
   - Beautiful category cards
   - Smooth animations

3. **Quiz Screen**
   - Question display
   - Multiple choice answers
   - Progress tracking
   - Score calculation

4. **Completion Screen**
   - Final score display
   - Percentage calculation
   - Retry option
   - Back to categories option

## Setup

1. Clone the repository:
```bash
git clone https://github.com/Coding-Meet/Quiz-App.git
```

2. Open in Android Studio

3. Run the app on an emulator or physical device


## API Integration

The app uses the Open Trivia Database API:
- Base URL: `https://opentdb.com/api.php`
- Supports multiple categories
- Various difficulty levels
- Multiple choice questions
