plugins {
    // Diese Aliase beziehen sich direkt auf deine [plugins] Sektion in der libs.versions.toml
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    // WICHTIG: Stelle sicher, dass dieser Namespace exakt zu deinem Package-Namen
    // in der MainActivity.kt passt!
    namespace = "com.deinname.mixersreise"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.deinname.mixersreise"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    buildFeatures {
        compose = true
    }

    // HINWEIS: Bei Kotlin 2.x ist KEIN composeOptions-Block mehr nötig!
    // Der Compose-Compiler wird nun über das plugin 'kotlin-compose' gesteuert.

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Basis-Bibliotheken aus deinem Version Catalog (libs.versions.toml)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // UI Komponenten
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // --- DEINE MANUELLEN ERGÄNZUNGEN ---

    // ViewModel Support für Compose (Wichtig für Mixers Herzen)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // GPS & Standortdienste
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Local Storage (DataStore) für Spielstände
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Standard Testing Bibliotheken
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}