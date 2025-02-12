plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
}

android {
    namespace = "com.aboolean.logintest.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.aboolean.logintest.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)

    implementation(libs.androidX.core)
    implementation(libs.androidX.activity)

    implementation(libs.material)

    // Koin-Dependency injection
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Leak Canary - Memory leaks
    debugImplementation(libs.leakCanary)

    testImplementation(libs.jUnitKtx)
    testImplementation(libs.kotlinX.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.archTestCore)
    testImplementation(libs.robolectric)

    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.runner)
}
