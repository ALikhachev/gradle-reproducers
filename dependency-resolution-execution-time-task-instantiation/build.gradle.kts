val attr1 = Attribute.of("myAttr1", String::class.java)

val resolvableConf = configurations.create("resolvableConf") {
    isCanBeResolved = true
    isCanBeConsumed = false

    attributes {
        attribute(attr1, "value1")
    }
}

abstract class ResolvingTask : DefaultTask() {
    @get:Internal
    lateinit var myInput: Provider<ResolvedComponentResult>

    @TaskAction
    fun doSmth() {
        myInput.get().dependencies
    }
}

dependencies {
    resolvableConf(project(":lib"))
}

tasks.register<ResolvingTask>("resolvingTask") {
    myInput = resolvableConf.incoming.resolutionResult.rootComponent
}