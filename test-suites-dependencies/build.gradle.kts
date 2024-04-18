plugins {
    `jvm-test-suite`
}

repositories {
    mavenCentral()
}

configurations.configureEach {
    if (isCanBeResolved && !isCanBeConsumed) {
        allDependencies.configureEach { // <-- removing this logic solves the problem

        }
    }
}

testing {
    suites {
        register<JvmTestSuite>("myAwesomeTests") {
            dependencies {
                implementation(project()) // the indirect cause
            }
        }
    }
}