plugins {
    id("app.app-convention")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":api:auth"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:pokemon"))
    implementation(project(":feature:profile"))
}
