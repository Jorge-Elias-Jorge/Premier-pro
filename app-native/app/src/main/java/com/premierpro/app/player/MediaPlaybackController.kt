package com.premierpro.app.player

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MediaPlaybackController(
    private val player: ExoPlayer
) {

    fun play() {
        player.playWhenReady = true
    }

    fun pause() {
        player.playWhenReady = false
    }

    fun toggle() {
        if (player.isPlaying) pause() else play()
    }

    fun seekTo(seconds: Double) {
        player.seekTo((seconds * 1000).toLong())
    }

    fun seekBy(deltaSeconds: Double) {
        val current = player.currentPosition / 1000.0
        seekTo((current + deltaSeconds).coerceAtLeast(0.0))
    }

    fun setSpeed(speed: Float) {
        player.setPlaybackSpeed(speed)
    }

    fun setMedia(uri: String) {
        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()
    }

    fun currentPositionSeconds(): Double {
        return player.currentPosition / 1000.0
    }

    fun durationSeconds(): Double {
        return player.duration / 1000.0
    }

    fun isPlaying(): Boolean {
        return player.isPlaying
    }

    fun release() {
        player.release()
    }
}
