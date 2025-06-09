plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "com.ms.compose.ux4gdesign"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.shankar51689"
                artifactId = "DesignSystemCompose"
                version = "1.0.0"

                // Explicitly include the Android library component
                from(components.findByName("release") ?: return@create)
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.compose.ui:ui:1.5.1")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.0")

    implementation( "androidx.compose.foundation:foundation:1.8.2")
    implementation( "androidx.compose.runtime:runtime-livedata:1.8.2")
    implementation( "com.google.accompanist:accompanist-pager:0.34.0")
    implementation( "com.google.accompanist:accompanist-pager-indicators:0.34.0")

    implementation ("io.coil-kt:coil-compose:2.4.0")

}