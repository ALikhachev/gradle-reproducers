# Buildscript classpath substitution

Gradle issue: https://github.com/gradle/gradle/issues/30198

## Steps to reproduce
1. Invoke `./gradlew publish -Pversion=1.0.0-dev-123` – it publishes the plugin to local repo directory
2. Invoke `./gradlew help -DapplyConventionPlugin=true -Pversion=1.0.0-dev-123` – it works fine.
3. Invoke `./gradlew help -DapplyConventionPlugin=true -Pversion=1.0.0-dev-1234` – it works fine.
4. Invoke `./gradlew help -DapplyConventionPlugin=true -Pversion=1.0.0` – it works fine.
5. Invoke `./gradlew help -DapplyConventionPlugin=true -Pversion=1.0.0-dev-12` – it fails with
```
A problem occurred configuring project ':plugin'.
> Could not resolve all artifacts for configuration ':plugin:classpath'.
   > Could not find org.jetbrains.kotlin:kotlin-stdlib:.
     Required by:
         project :plugin > project :build-logic:convention-plugin > org.example:plugin:1.0.0-dev-123

```
https://scans.gradle.com/s/hzbtc7phg5hme/build-dependencies?toggled=W1syXSxbMiwwXSxbMiwwLFs2OF1dLFsyLDAsWzY4LDY5XV1d
![buildscript-classpath-1.png](buildscript-classpath-1.png)
6. Invoke `./gradlew help -DapplyConventionPlugin2=true -Pversion=1.0.0-dev-12` – it works fine (in that case the convention plugin is applied to a similar project with different coordinates)

Note that in the successful case (e.g. from step 2) there's something strange in the buildscript classpath:
https://scans.gradle.com/s/74falrrzxzbvs/build-dependencies?toggled=W1syXSxbMiwwXSxbMiwwLFs2OF1dLFsyLDAsWzY4LDY5XV1d
![buildscript-classpath-2.png](buildscript-classpath-2.png)

Also, a deprecation warning is produced containing purely Gradle stacktrace:
```
The classpath configuration has been deprecated for consumption. This will fail with an error in Gradle 9.0. For more information, please refer to https://docs.gradle.org/8.10/userguide/declaring_dependencies.html#sec:deprecated-configurations in the Gradle documentation.
While resolving configuration 'classpath', it was also selected as a variant. Configurations should not act as both a resolution root and a variant simultaneously. Depending on the resolved configuration in this manner has been deprecated. This will fail with an error in Gradle 9.0. Be sure to mark configurations meant for resolution as canBeConsumed=false or use the 'resolvable(String)' configuration factory method to create them. Consult the upgrading guide for further information: https://docs.gradle.org/8.10/userguide/upgrading_version_8.html#depending_on_root_configuration

```

The problem seems specific to kotlin-stdlib and kotlin-reflect.
For example, it does not happen with `org.jetbrains.kotlin:kotlin-build-tools-api:2.0.20-RC2` or `com.google.code.gson:gson:2.11.0`