abstract class MyTask : DefaultTask() {
    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun smth() {
        outputFile.get().asFile.createNewFile()
    }
}

val task1 = tasks.register("task1", MyTask::class.java) {
    println("$path is instantiated")
    outputFile.set(project.layout.buildDirectory.file("output1.txt"))
}

val attr1 = Attribute.of("myAttr1", String::class.java)

val conf = configurations.create("consumableConf") {
    isCanBeResolved = false
    isCanBeConsumed = true

    attributes {
        attribute(attr1, "value1")
    }
}

conf.outgoing.artifact(task1.flatMap { it.outputFile }) {
    classifier = null
    type = "txt"
    extension = "txt"
    name = "test"
}

