package base

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import gradle.kotlin.dsl.accessors._1a8cfdda1d7370eb30824f37e6cd59c1.kotlin
import util.AppConfig
import util.baseDependencies

plugins {
    id ("org.jetbrains.kotlin.plugin.serialization")
}

pluginManager.withPlugin("com.android.application") {
    extensions.configure<ApplicationExtension> {
        namespace = "${AppConfig.projectNameSpace}.${project.name.replace("-", ".")}"
        compileSdk = AppConfig.compileSdk

        defaultConfig {
            minSdk = AppConfig.minSdk
            targetSdk = AppConfig.targetSdk
            versionCode = AppConfig.versionCode
            applicationId = AppConfig.projectNameSpace
            resValue("string", "app_name", AppConfig.appName)

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildFeatures {
            buildConfig = true
            resValues = true
        }

        compileOptions {
            sourceCompatibility = AppConfig.javaVersion
            targetCompatibility = AppConfig.javaVersion
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }
        }
    }
}

pluginManager.withPlugin("com.android.library") {
    extensions.configure<LibraryExtension> {
        namespace = "${AppConfig.projectNameSpace}.${project.name.replace("-", ".")}"
        compileSdk = AppConfig.compileSdk

        defaultConfig {
            minSdk = AppConfig.minSdk
        }

        buildFeatures {
            buildConfig = true
            resValues = true
        }

        compileOptions {
            sourceCompatibility = AppConfig.javaVersion
            targetCompatibility = AppConfig.javaVersion
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }
        }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add(
            "-Xannotation-default-target=param-property"
        )
    }
}

baseDependencies()