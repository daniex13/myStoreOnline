# MyStoreOnline

This repository is the android mobile app for project MyStoreOnline, for walmart
The project is built with Kotlin and follow
the [Android Architectural Guidelines](https://developer.android.com/topic/architecture/intro).

## Configuration


## Run the app

### With Android Studio/IntelliJ

- Open this directory in Android Studio.
- Wait for gradle to sync dependencies.
- Build & run the app

### Command Line

- On the project directory run ```./gradlew build```
- Run the emulator with emulator ```emulator -avd mck\ pixel\ 5\ api31```
- Run ```adb install app/build/outputs/apk/debug/app-debug.apk```

## Finding out appActivity and appPackage for testing

```shell 
adb shell dumpsys window windows | grep ActivityRecord
```

## Architectural Overview

This projects uses
the [MVVM](https://developer.android.com/topic/architecture?hl=pt-br#recommended-app-arch)
architectural pattern.
I also
use [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
layers to separate split responsabilities and better structure architectural dependencies.
This project is with jetpack compose, use hilt, compose navigation, unit test with mockk, 
use retrofit to call apis, storage cart with room and call url image with coil

## Known Issues

