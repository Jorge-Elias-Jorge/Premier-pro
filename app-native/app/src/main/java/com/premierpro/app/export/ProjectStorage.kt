package com.premierpro.app.export

import android.content.Context
import java.io.File

class ProjectStorage(
    private val context: Context
) {

    private val projectsDir: File
        get() = File(context.filesDir, "projects").apply { mkdirs() }

    fun saveProject(
        projectId: String,
        content: String
    ): File {
        val file = File(projectsDir, "$projectId.json")
        file.writeText(content)
        return file
    }

    fun loadProject(projectId: String): String? {
        val file = File(projectsDir, "$projectId.json")
        return if (file.exists()) file.readText() else null
    }

    fun listProjects(): List<String> {
        return projectsDir.listFiles()
            ?.filter { it.extension == "json" }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()
    }

    fun deleteProject(projectId: String): Boolean {
        val file = File(projectsDir, "$projectId.json")
        return if (file.exists()) file.delete() else false
    }
}
