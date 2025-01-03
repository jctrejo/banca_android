plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hiltPlugin)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.android.mibanca"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.mibanca"
        minSdk = 24
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
configurations.implementation{
    exclude(group = "com.intellij", module = "annotations")
}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.compiler)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //OkHttp
    implementation(libs.okhttp.v4110)
    implementation(libs.okhttp.urlconnection)
    implementation(libs.logging.interceptor)

    //Gson
    implementation(libs.gson.v2101)

    implementation ("androidx.compose.ui:ui:1.0.0")
    implementation ("androidx.compose.material:material:1.0.0")
    implementation("androidx.navigation:navigation-compose:2.8.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.3")

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    //Retrofit
    implementation(libs.logging.interceptor)
    implementation (libs.okhttp.urlconnection)
    implementation(libs.adapter.rxjava2)
    implementation(libs.converter.gson)

    //Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //Jetpack Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Firebase
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-config-ktx:22.0.1")
    implementation("com.google.firebase:firebase-firestore:24.1.2")
    implementation("com.google.firebase:firebase-auth:19.2.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.0.1")
    implementation("com.google.firebase:firebase-database-ktx:20.0.5")
}
