plugins {
    id("app.app-convention")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":api-a"))
    implementation(project(":feature-a"))
}
