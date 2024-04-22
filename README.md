
# Baubap Coding Challenge

Kotlin multiplatform application on Android and IOS to simulate login feature. 


<h2>Project structure</h2>
  The project structure contains the following layers as follows in the next diagram:
<img width="500" height="500" alt="diagram" src="https://github.com/oscarito9410/LoginTest/assets/13366923/04ae33cb-4612-4d28-8d02-06013d2b1e29">

The core principles of the clean approach can be summarized as followed:

1. The application code is separated into layers.

These layers define the separation of concerns inside the code base.

2. The layers follow a strict dependency rule.

Each layer can only interact with the layers below it.


<h2>Test directories</h2>

<h3>androidUnitTest</h3>

Due to KMP doesn't have offical support for any Mock framework. I decided to move tests that needs dependencies into **androidUnitTest** directory as below:

<img width="598" alt="Captura de Pantalla 2024-04-21 a la(s) 23 19 01" src="https://github.com/oscarito9410/LoginTest/assets/13366923/b88b6766-38d5-43c8-af9f-05eaad16030e">

This directory contains unit tests for the **ViewModel**, **repository**, **datasource**, and **useCase** classes. These tests are focused on testing the individual components and their behavior in isolation.

<h3>commonTest</h3>

these tests are platform-agnostic and compatible with any supported framework. For this code challenge, I have decided to maintain tests for **extensions**, **mappers**, and **compose components** in this directory, as required by the framework for Compose tests.

<img width="524" alt="Captura de Pantalla 2024-04-21 a la(s) 23 36 42" src="https://github.com/oscarito9410/LoginTest/assets/13366923/682fd71d-39b9-4e78-b1b8-aa08cf6cadf6">

To run all the tests in the directory, run the following command

```bash
  ./gradlew shared:allTests
```

<h3>UI compose tests into commonTest</h3>

This project is using the [experimental API](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html#how-compose-multiplatform-testing-is-different-from-jetpack-compose) for UI testing however its important to take into consideration that:

> [!WARNING]
*Currently, you cannot run common Compose Multiplatform tests using android (local) test configurations, so gutter icons in Android Studio, for example, won't be helpful*

To run UI tests in IOS use

```bash
./gradlew :composeApp:iosSimulatorArm64Test
```

To run UI tests in Android use
```bash
./gradlew :shared:connectedAndroidTest
```

<h3>UI tests running showcase</h3>


https://github.com/oscarito9410/LoginTest/assets/13366923/8518e03e-c34b-4217-87ac-ed77d5026d75


<h3>Test result report for UI tests</h3>
<img width="1432" alt="Captura de Pantalla 2024-04-22 a la(s) 0 00 42" src="https://github.com/oscarito9410/LoginTest/assets/13366923/2313d5f7-d5af-4929-ac49-46ff2258cefb">



<h3>Test result report for unit tests</h3>

<img width="1436" alt="Captura de Pantalla 2024-04-21 a la(s) 23 42 53" src="https://github.com/oscarito9410/LoginTest/assets/13366923/9c5a3559-4c85-4a7b-b6a4-7ec516765071">



# Quality Code

This repository is scanned on every commit using SonarCloud. The quality gate status and bug count for the latest code analysis are as follows:

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=oscarito9410_android_test_v2&metric=alert_status&token=9dea55bf7e466e75d5e3a1b021725c6f1fb4e07f)](https://sonarcloud.io/summary/new_code?id=oscarito9410_android_test_v2) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=oscarito9410_android_test_v2&metric=bugs&token=9dea55bf7e466e75d5e3a1b021725c6f1fb4e07f)](https://sonarcloud.io/summary/new_code?id=oscarito9410_android_test_v2)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=oscarito9410_android_test_v2&metric=code_smells&token=9dea55bf7e466e75d5e3a1b021725c6f1fb4e07f)](https://sonarcloud.io/summary/new_code?id=oscarito9410_android_test_v2)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=oscarito9410_android_test_v2&metric=security_rating&token=9dea55bf7e466e75d5e3a1b021725c6f1fb4e07f)](https://sonarcloud.io/summary/new_code?id=oscarito9410_android_test_v2)

Additionally, I've added a CI pipeline using GitHub Actions to run tests in the project on every commit

[![Android CI Tests Review](https://github.com/oscarito9410/android_test_v2/actions/workflows/android.yml/badge.svg)](https://github.com/oscarito9410/android_test_v2/actions/workflows/android.yml)

## App Show case on Android and iOS



https://github.com/oscarito9410/LoginTest/assets/13366923/cb1198b5-d045-465e-a62b-facf20909e60

