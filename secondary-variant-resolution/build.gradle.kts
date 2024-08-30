interface VariantType : Named {
    companion object {
        @JvmField
        val ATTRIBUTE = Attribute.of(VariantType::class.javaObjectType)

        @JvmField
        val PRIMARY = "primary"

        @JvmField
        val SECONDARY = "secondary"
    }
}

val myDependencyScope = configurations.dependencyScope("myDependencyScope").get()

val useRegisterForSecondaryVariant = providers.gradleProperty("registerForSecondaryVariant").orNull.toBoolean()
val addCompatibilityRule = providers.gradleProperty("addCompatibilityRule").orNull.toBoolean()

configurations {
    consumable("myConsumableConfiguration") {
        attributes.attribute(VariantType.ATTRIBUTE, objects.named(VariantType.PRIMARY))
        outgoing.artifact(file("file1.txt"))
        if (useRegisterForSecondaryVariant) {
            outgoing.variants.register("my-secondary-variant") {
                attributes.attribute(VariantType.ATTRIBUTE, objects.named(VariantType.SECONDARY))
                artifact(file("file2.txt"))
            }
        } else {
            outgoing.variants.create("my-secondary-variant") {
                attributes.attribute(VariantType.ATTRIBUTE, objects.named(VariantType.SECONDARY))
                artifact(file("file2.txt"))
            }
        }
    }
}

val myResolvableConfiguration = configurations.resolvable("myResolvableConfiguration") {
    extendsFrom(myDependencyScope)
    attributes.attribute(VariantType.ATTRIBUTE, objects.named(VariantType.SECONDARY))
}

abstract class VariantTypeAttributeCompatibilityRule : AttributeCompatibilityRule<VariantType> {
    override fun execute(details: CompatibilityCheckDetails<VariantType>) = details.run {
        if (consumerValue?.name == VariantType.SECONDARY && producerValue?.name == VariantType.PRIMARY) {
            compatible()
        }
    }
}

dependencies {
    attributesSchema {
        attribute(VariantType.ATTRIBUTE) {
            if (addCompatibilityRule) {
                compatibilityRules.add(VariantTypeAttributeCompatibilityRule::class.java)
            }
        }
    }
    myDependencyScope(project(":"))
}

tasks.register("myTask") {
    dependsOn(myResolvableConfiguration)
    inputs.files(myResolvableConfiguration)
    doFirst {
        println(myResolvableConfiguration.get().files)
    }
}