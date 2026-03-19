plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.deinname.mixersreise"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.deinname.mixersreise"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Wir lassen den TestRunner drin, falls du später Tests schreibst
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
}

dependencies {
    // UI & Lifecycle (Mixers Herzschlag)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)

    // Room Database (Mixers Gedächtnis)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Work & Location (Mixers Reise-Logik)
    implementation(libs.androidx.work.ktx)
    implementation(libs.play.services.location)
    implementation(libs.maps.compose)

    // Test-Bibliotheken (Stark gekürzt)
    testImplementation(libs.junit)
}