plugins {
    `java-gradle-plugin`
    `maven-publish`
    if (System.getProperty("applyConventionPlugin") == "true") {
        id("convention-plugin")
    }
}

repositories {
    maven(rootDir.resolve("repo"))
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20-RC2")
}

gradlePlugin {
    plugins {
        register("org.example.my-plugin") {
            id = "org.example.my-plugin"
            implementationClass = "org.example.MyPlugin"
        }
    }
}

publishing {
    repositories {
        maven(rootDir.resolve("repo"))
    }
}