plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.todoapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todoapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //using navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // using ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // using LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // using RecyclerView
    implementation(libs.recyclerview)
    // For control over item selection of both touch and mouse driven selection
    implementation(libs.recyclerview.selection)
    // using Room
    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    // For Kotlin projects
    ksp(libs.androidx.room.compiler)
}