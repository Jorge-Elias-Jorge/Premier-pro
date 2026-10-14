package com.premierpro.app.timeline

import android.net.Uri

enum class ClipType {
    VIDEO,
    AUDIO,
    IMAGE
}

/**
 * Um clipe é a menor unidade editável da timeline.
 */
data class Clip(
    val id: String,
    val name: String,
    val type: ClipType,
    val uri: Uri,
    val mediaDurationSeconds: Double,
    val trimStartSeconds: Double = 0.0,
    val trimEndSeconds: Double = mediaDurationSeconds,
    val textOverlay: String = ""
) {
    val durationSeconds: Double
        get() = (trimEndSeconds - trimStartSeconds).coerceAtLeast(0.0)

    val isPlayable: Boolean
        get() = type != ClipType.IMAGE

    fun containsPlayhead(timeSeconds: Double): Boolean {
        return timeSeconds >= trimStartSeconds && timeSeconds < trimEndSeconds
    }

    fun splitAt(timeSeconds: Double): Pair<Clip, Clip>? {
        if (!containsPlayhead(timeSeconds)) return null
        val left = copy(trimEndSeconds = timeSeconds)
        val right = copy(
            id = id + "-split",
            trimStartSeconds = timeSeconds
        )
        return left to right
    }
}
