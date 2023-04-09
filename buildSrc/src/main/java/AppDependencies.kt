import org.gradle.api.artifacts.dsl.DependencyHandler

object AppLibraries: DependencyList() {

    //std lib
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //android ui
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"

    //compose
    val composeConstraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    val composeActivity = "androidx.activity:activity-compose:1.7.0"
    val composeIcons = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    val composeGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"

    //navigation
    val composeNavigation = "androidx.navigation:navigation-compose:2.5.3"

    //other
    val pieChartLib = "com.github.PhilJay:MPAndroidChart:v3.1.0"
    val retrofit = "com.squareup.retrofit2:retrofit:2.6.4"
    val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:2.3.0"
    val accompanist = "com.google.accompanist:accompanist-swiperefresh:0.23.0"
    val lazyHorizontalGrid = "androidx.compose.foundation:foundation:1.3.1"
    val calendar = "com.kizitonwose.calendar:compose:2.1.1"
    val asyncImage = "io.coil-kt:coil-compose:2.2.0"
    
}

object DebugLibraries: DependencyList() {

    //compose-debug
    private val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    private val composeTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    
}

object DesugaringLibraries: DependencyList() {
    
    val desugarJdk = "com.android.tools:desugar_jdk_libs:${Versions.desugarJdkVersion}"
    
}

object AndroidTestLibraries: DependencyList() {

    //test libs
    val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    
}

object TestLibraries: DependencyList() {
    //test libs
    private val junit = "junit:junit:${Versions.junit}"
}

/**
 * Старый подход
 * создать переменную список зависимостей
 * раньше это был целый класс,  котором объявлялись переменные
 */


//util functions for adding the different type dependencies from build.gradle file

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.kapt(list: DependencyList){
    this.kapt(list.list())
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.implementation(list: DependencyList){
    this.implementation(list.list())
}


fun DependencyHandler.testImplementation(list: List<String>){
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: DependencyList){
    this.testImplementation(list.list())
}


fun DependencyHandler.androidTestImplementation(list: List<String>){
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: DependencyList){
    this.androidTestImplementation(list.list())
}

fun DependencyHandler.coreLibraryDesugaring(list: List<String>){
    list.forEach { dependency ->
        add("coreLibraryDesugaring", dependency)
    }
}

fun DependencyHandler.coreLibraryDesugaring(list: DependencyList){
    this.coreLibraryDesugaring(list.list())
}

fun DependencyHandler.debugImplementation(list: List<String>){
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}

fun DependencyHandler.debugImplementation(list: DependencyList){
    this.debugImplementation(list.list())
}
