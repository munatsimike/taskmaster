// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies{
        classpath(libs.hilt.android.gradle.plugin)
        classpath (libs.kotlin.serialization)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id ("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
}