plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "ru.megboyzz.dnevnik"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        version = AppConfig.versionName

        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true

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
        freeCompilerArgs = listOf(
            "-Xjvm-default=compatibility"
        )
    }
    buildFeatures{
        compose = true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = Versions.compose
    }

    packagingOptions{
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

tasks.register("connectToWSA"){

}

dependencies {

    //std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //app libs
    implementation(AppLibraries)
    //test libs
    testImplementation(TestLibraries)
    androidTestImplementation(AndroidTestLibraries)

    kapt(KaptLibraries)

    coreLibraryDesugaring(DesugaringLibraries)
    debugImplementation(DebugLibraries)
}