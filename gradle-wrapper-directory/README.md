# Bug in Gradle wrapper's parser

## A build with the default Gradle user home

Ensure there are no running daemons for clean experiment

Invoke `./gradlew :printGradleDistLibraryPath`

You will see a path like `%USER_HOME_DIRECTORY%/.gradle/wrapper/dists/gradle-8.8-bin/dl7vupf4psengwqhwktix4v1/gradle-8.8/lib/gradle-cli-8.8.jar`

## A build with an overridden Gradle user home via CLI option as first argument

Ensure there are no running daemons for clean experiment

Invoke `./gradlew --gradle-user-home=./home :printGradleDistLibraryPath`

This works as expected, both Gradle and Gradle wrapper respect the new value. The observed path will be something like `%PROJECT_DIRECTORY%/home/wrapper/dists/gradle-8.8-bin/dl7vupf4psengwqhwktix4v1/gradle-8.8/lib/gradle-cli-8.8.jar`

## A build with an overridden Gradle user home via CLI option after tasks list

Ensure there are no running daemons for clean experiment

Invoke `./gradlew :printGradleDistLibraryPath --gradle-user-home=./home`

You will again see a path like `%USER_HOME_DIRECTORY%/.gradle/wrapper/dists/gradle-8.8-bin/dl7vupf4psengwqhwktix4v1/gradle-8.8/lib/gradle-cli-8.8.jar`, however the distribution from custom Gradle user home should be used instead.