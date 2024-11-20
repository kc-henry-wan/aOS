plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}

android {
    namespace = "com.wellbeing.pharmacyjob"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wellbeing.pharmacyjob"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // BuildConfig.DEBUG will be true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Jetpack Compose libraries
    implementation("androidx.compose.ui:ui:1.7.3")
    implementation("androidx.compose.material:material:1.7.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.3")
    implementation("androidx.recyclerview:recyclerview:1.3.0")

    // Add dependencies for Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Kotlin Standard Library
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("com.google.android.material:material:1.3.0")
    implementation("com.google.android.gms:play-services-location:19.0.1") // For location services

    implementation("com.jakewharton.timber:timber:5.0.1") // Add this line for Timber logging
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("io.coil-kt:coil:2.4.0")
}
