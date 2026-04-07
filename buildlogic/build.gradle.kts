import org.gradle.kotlin.dsl.implementation

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.symbol.processing.gradle.plugin)
    implementation(libs.hilt.android.gradle.plugin)
    implementation(libs.kotlin.compose.compiler.gradle.plugin)
    implementation(libs.kotlin.serialization)
//    implementation(libs.room.gradle.plugin)
}