plugins {
    id("core-convention")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode"))
    }
}
