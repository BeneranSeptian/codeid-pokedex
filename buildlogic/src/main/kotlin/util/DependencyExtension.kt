package util

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

fun DependencyHandlerScope.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandlerScope.ksp(dependencyNotation: Any): Dependency? =
    add("ksp", dependencyNotation)

fun DependencyHandlerScope.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

fun DependencyHandlerScope.debugImplementation(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

fun DependencyHandlerScope.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandlerScope.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun Project.unitTestDependencies() {
    val libs = the<LibrariesForLibs>()

    dependencies {
        testImplementation(libs.junit)
        testImplementation(libs.mockk)
        testImplementation(libs.kotlinx.coroutines.test)
        testImplementation(libs.turbine)
    }
}

fun Project.baseDependencies() {
    val libs = the<LibrariesForLibs>()

    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.timber)
    }
}

fun Project.hiltDependencies() {
    val libs = the<LibrariesForLibs>()

    dependencies {
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)
    }
}

fun Project.composeDependencies() {
    val libs = the<LibrariesForLibs>()

    dependencies {
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        debugImplementation(libs.androidx.compose.ui.tooling)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.hilt.navigation.compose)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.compose.material.icons.extended)
        implementation(libs.androidx.lifecycle.runtime.ktx)
    }
}

fun Project.networkingDependencies() {
    val libs = the<LibrariesForLibs>()

    dependencies {
        implementation(libs.retrofit.core)
        implementation(libs.retrofit.kotlin.serialization)
        implementation(libs.okhttp.logging)
    }
}

fun Project.localStorageDependencies() {
    val libs = the<LibrariesForLibs>()

    dependencies {
//        implementation(libs.room.runtime)
//        implementation(libs.room.ktx)
//        ksp(libs.room.compiler)
        implementation(libs.datastore.preferences)
    }
}
