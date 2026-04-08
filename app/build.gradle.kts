plugins {
    id("app.app-convention")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":api:auth"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:splash"))
}
