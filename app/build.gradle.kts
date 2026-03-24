plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Compose Compiler ist ab Kotlin 2.0 fest integriert,
    // falls du eine ältere Version nutzt, lass das Plugin drin
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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // WICHTIG für Room: Exportieren des Schemas (optional, aber guter Stil)
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        // Upgrade auf Java 17 (empfohlen für aktuelle Compose/Android Versionen)
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        // Falls du später ViewBinding für Maps-Fragmente brauchst
        viewBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --- UI & Lifecycle ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)

    // Compose Material 3 & Icons (Wichtig für TopBar Icons)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // Ergänzung: Erweiterte Icons für Zahnrad, Globus etc.
    implementation("androidx.compose.material:material-icons-extended")

    // --- Room Database ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

// --- DER ABSOLUT EINZIGE ZUSATZ FÜR GPS ---
    implementation("com.google.android.gms:play-services-location:21.2.0")

    // --- Reise & Location ---
    implementation(libs.androidx.work.ktx)
    // Location Services für die Multiplikator-Berechnung
    implementation(libs.play.services.location)

    // Google Maps für Android (Compose Version)
    implementation(libs.maps.compose)
    // Zusätzlich das Google Play Services Maps SDK
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}