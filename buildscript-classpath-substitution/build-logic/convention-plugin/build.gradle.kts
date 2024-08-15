plugins {
    `kotlin-dsl`
}

repositories {
    maven(rootDir.parentFile.resolve("repo"))
    gradlePluginPortal()
}

dependencies {
    implementation("org.example:plugin:1.0.0-dev-123")
}