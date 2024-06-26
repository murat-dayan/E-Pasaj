buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinAndroidKsp) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}