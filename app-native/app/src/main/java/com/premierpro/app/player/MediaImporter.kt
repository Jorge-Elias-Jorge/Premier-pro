package com.premierpro.app.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MediaImporter(
    private val context: Context
) {

    fun importVideo(uri: Uri): ImportedMedia {
        val player = ExoPlayer.Builder(context).build()
        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()

        val duration = player.duration.coerceAtLeast(0L)
        player.release()

        return ImportedMedia(
            uri = uri.toString(),
            type = MediaType.VIDEO,
            durationSeconds = duration / 1000.0
        )
    }

    fun importAudio(uri: Uri): ImportedMedia {
        val player = ExoPlayer.Builder(context).build()
        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()

        val duration = player.duration.coerceAtLeast(0L)
        player.release()

        return ImportedMedia(
            uri = uri.toString(),
            type = MediaType.AUDIO,
            durationSeconds = duration / 1000.0
        )
    }

    fun detectType(uri: Uri): MediaType {
        val type = context.contentResolver.getType(uri) ?: ""
        return if (type.startsWith("video/")) MediaType.VIDEO else MediaType.AUDIO
    }
}

enum class MediaType {
    VIDEO,
    AUDIO
}

data class ImportedMedia(
    val uri: String,
    val type: MediaType,
    val durationSeconds: Double
)
