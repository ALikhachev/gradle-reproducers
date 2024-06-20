val myAttribute1 = Attribute.of("myAttribute1", String::class.java)
val myAttribute2 = Attribute.of("myAttribute2", String::class.java)

configurations.consumable("consumableConfiguration1") {
    attributes {
        attribute(myAttribute1, "someOtherValue1")
        attribute(myAttribute2, "value2")
    }
}

val consumableConfiguration = configurations.consumable("consumableConfiguration") {
    attributes {
        attribute(myAttribute1, "value1")
        attribute(myAttribute2, "value2")
    }

    if (providers.gradleProperty("registerSecondaryVariant").orNull == "true") {
        outgoing.variants.create("myVariant") {
            attributes {
                attribute(myAttribute1, "value1")
                attribute(myAttribute2, "otherValue2")
            }
        }
    }
}

if (providers.gradleProperty("registerArtifacts").orNull == "true") {
    artifacts {
        add(consumableConfiguration.name, project.file("build.gradle.kts"))
    }
}

val dependencyScope = configurations.dependencyScope("dependencyScope") {

}.get()

val resolvableConfiguration = configurations.resolvable("resolvableConfiguration") {
    extendsFrom(dependencyScope)
    attributes {
        attribute(myAttribute1, "value1")
        attribute(myAttribute2, "value2")
    }
}

dependencies {
    attributesSchema.attribute(myAttribute1)
    attributesSchema.attribute(myAttribute2)
    dependencyScope(project(":"))
}

tasks.register("resolve") {
    inputs.files(resolvableConfiguration)
    doFirst {
        println(resolvableConfiguration.get().files)
    }
}