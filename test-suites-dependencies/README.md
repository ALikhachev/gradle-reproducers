# JVM Test Suit + iteration over declared dependencies

To reproduce the issue, just invoke `./gradlew`

```
FAILURE: Build failed with an exception.

* Where:
Build file '[redacted]/test-suites-dependencies/build.gradle.kts' line: 26

* What went wrong:
Could not create domain object 'myAwesomeTests' (JvmTestSuite)
> The value for property 'dependencies.implementation' property 'dependencies' is final and cannot be changed any further.

```