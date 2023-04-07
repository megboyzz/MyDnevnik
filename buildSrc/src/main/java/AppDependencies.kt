import org.gradle.api.artifacts.dsl.DependencyHandler

//TODO - придумать обработчик зависмостей основанный на рефлексии
object AppDependencies {
    //std lib
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //android ui
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"

    //compose
    private val composeConstraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    private val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    private val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    private val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    private val composeActivity = "androidx.activity:activity-compose:1.7.0"
    private val composeIcons = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    private val composeGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"

    //compose-debug
    private val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    private val composeTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"

    //navigation
    private val composeNavigation = "androidx.navigation:navigation-compose:2.5.3"

    //other
    private val pieChartLib = "com.github.PhilJay:MPAndroidChart:v3.1.0"
    private val retrofit = "com.squareup.retrofit2:retrofit:2.6.4"
    private val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:2.3.0"
    private val accompanist = "com.google.accompanist:accompanist-swiperefresh:0.23.0"
    private val lazyHorizontalGrid = "androidx.compose.foundation:foundation:1.3.1"
    private val calendar = "com.kizitonwose.calendar:compose:2.1.1"
    private val asyncImage = "io.coil-kt:coil-compose:2.2.0"

    //test libs
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    //desugaring
    private val desugarJdk = "com.android.tools:desugar_jdk_libs:${Versions.desugarJdkVersion}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(composeConstraintLayout)
        add(composeUi)
        add(composeMaterial)
        add(composePreview)
        add(composeActivity)
        add(composeIcons)
        add(pieChartLib)
        add(composeNavigation)
        add(retrofit)
        add(retrofitConverterGson)
        add(accompanist)
        add(lazyHorizontalGrid)
        add(asyncImage)
        add(calendar)
    }

    val desugaringLibraries = arrayListOf<String>().apply {
        add(desugarJdk)
    }

    val debugLibraries = arrayListOf<String>().apply {
        add(composeUiTooling)
        add(composeTestManifest)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}


//util functions for adding the different type dependencies from build.gradle file

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>){
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>){
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.coreLibraryDesugaring(list: List<String>){
    list.forEach { dependency ->
        add("coreLibraryDesugaring", dependency)
    }
}

fun DependencyHandler.debugImplementation(list: List<String>){
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}