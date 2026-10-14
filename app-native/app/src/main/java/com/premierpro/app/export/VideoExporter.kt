package com.premierpro.app.export

import android.content.Context
import android.net.Uri

interface VideoExporter {
    fun export(
        context: Context,
        mediaUris: List<Uri>,
        outputFileName: String,
        onProgress: (Float) -> Unit,
        onFinished: (Uri?) -> Unit
    )
}

class BasicVideoExporter : VideoExporter {

    override fun export(
        context: Context,
        mediaUris: List<Uri>,
        outputFileName: String,
        onProgress: (Float) -> Unit,
        onFinished: (Uri?) -> Unit
    ) {
        onProgress(0f)
        onFinished(null)
    }
}
