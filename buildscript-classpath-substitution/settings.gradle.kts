pluginManagement {
    repositories {
        maven(File("repo"))
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}
rootProject.name = "buildscript-classpath-substitution"
include(":plugin")
include(":plugin-2")