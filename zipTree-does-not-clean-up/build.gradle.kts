abstract class MyTask : DefaultTask() {
    @get:InputFile
    abstract val inputFile: RegularFileProperty

    @get:Inject
    abstract val archiveOperations: ArchiveOperations

    @TaskAction
    fun taskAction() {
        archiveOperations.zipTree(inputFile.get().asFile).visit {
            println(path)
            file.inputStream().use {
                // required to unpack the files
            }
        }
    }
}

tasks.register("listGradleWrapperJarFiles", MyTask::class.java) {
    inputFile = layout.projectDirectory.file("gradle/wrapper/gradle-wrapper.jar")
}