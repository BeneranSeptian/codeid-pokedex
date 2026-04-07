package util

import org.gradle.api.JavaVersion

object AppConfig {
    const val appName = "Pokedex"
    const val projectNameSpace = "dev.septianbeneran.technicaltest"
    const val compileSdk = 36
    const val minSdk = 24
    const val targetSdk = 36
    const val versionCode = 1
    const val flavorDimension = "environment"
    val javaVersion = JavaVersion.VERSION_21
}

enum class ProductFlavor(val flavor: String, val suffix: String? = null) {
    DEV("dev", ".dev"),
    UAT("uat", ".uat"),
    BETA("beta", ".beta"),
    PROD("prod")
}