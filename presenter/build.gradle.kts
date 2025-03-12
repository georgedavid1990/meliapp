plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.jadc.presenter"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "com.jadc.presenter.base.testing.InstrumentationTestRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }

    packaging {
        resources {
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material3)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.navigation)
    implementation(platform(libs.compose.bom))
    implementation(libs.koin.compose)
    implementation(libs.coil.compose)
    implementation(libs.ui.tooling)
    implementation(libs.ui.graphics)
    implementation(libs.activity.compose)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.material3.windowsizeclass)
    implementation(libs.accompanist.systemuicontroller)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.config)
    implementation(libs.firebase.analytics)
    implementation(libs.leakcanary.android)
    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.paging.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.mockk)
    debugImplementation(libs.ui.test.manifest)

    api(libs.testing.core)
    api(libs.testing.runner)
    api(libs.testing.rules)
}