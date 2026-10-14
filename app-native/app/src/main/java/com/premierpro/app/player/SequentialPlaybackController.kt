package com.premierpro.app.player

import com.premierpro.app.timeline.Clip

class SequentialPlaybackController {

    fun totalDuration(clips: List<Clip>): Double {
        return clips.sumOf { it.trimEndSeconds - it.trimStartSeconds }
    }

    fun clipAtTime(clips: List<Clip>, globalSeconds: Double): Clip? {
        var accumulated = 0.0
        for (clip in clips) {
            val duration = clip.trimEndSeconds - clip.trimStartSeconds
            if (globalSeconds < accumulated + duration) {
                return clip
            }
            accumulated += duration
        }
        return null
    }

    fun localTimeInClip(clip: Clip, globalSeconds: Double): Double {
        var accumulated = 0.0
        val duration = clip.trimEndSeconds - clip.trimStartSeconds
        if (globalSeconds < accumulated + duration) {
            return clip.trimStartSeconds + (globalSeconds - accumulated)
        }
        return clip.trimStartSeconds
    }

    fun nextClip(clips: List<Clip>, currentClipId: String): Clip? {
        val index = clips.indexOfFirst { it.id == currentClipId }
        if (index == -1 || index + 1 >= clips.size) return null
        return clips[index + 1]
    }

    fun previousClip(clips: List<Clip>, currentClipId: String): Clip? {
        val index = clips.indexOfFirst { it.id == currentClipId }
        if (index <= 0) return null
        return clips[index - 1]
    }
}
