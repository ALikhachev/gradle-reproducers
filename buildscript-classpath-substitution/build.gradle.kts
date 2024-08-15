group = "org.example"
version = project.providers.gradleProperty("version").get()

allprojects {
    group = rootProject.group
    version = rootProject.version
}