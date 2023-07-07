import org.gradle.api.JavaVersion


/**
The AndroidSettings object holds the configuration settings for the Android project.
It provides constants and values related to the Android SDK versions and compatibility.
 */
object AndroidSettings {
    // Minimum SDK version required by the application
    const val minSdkVersion = 21

    // Compile SDK version used for building the application
    const val compileSdkVersion = 33

    // Target SDK version for the application
    const val targetSdkVersion = 33

    // Source compatibility level for Java code
    val sourceCompatibility = JavaVersion.VERSION_17

    // Target compatibility level for Java code
    val targetCompatibility = JavaVersion.VERSION_17

    // Test instrumentation runner for Android JUnit tests
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
