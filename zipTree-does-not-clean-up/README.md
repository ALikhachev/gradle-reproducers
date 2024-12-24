# zipTree does not clean up temporary directory

1. Invoke `./gradlew :listGradleWrapperJarFiles`
2. Take a look at `build/tmp/.cache/expanded/`. There is an expanded version of the gradle-wrapper.jar. It remains there until explicit cleanup is invoked.

Gradle issue: https://github.com/gradle/gradle/issues/31881