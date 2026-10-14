package com.premierpro.app.player

import com.premierpro.app.timeline.Clip

class TouchTrimController {

    fun setStartToPlayhead(clip: Clip, playheadSeconds: Double): Clip {
        val safeStart = playheadSeconds
            .coerceIn(0.0, clip.trimEndSeconds - MIN_DURATION)
        return clip.copy(trimStartSeconds = safeStart)
    }

    fun setEndToPlayhead(clip: Clip, playheadSeconds: Double): Clip {
        val safeEnd = playheadSeconds
            .coerceIn(clip.trimStartSeconds + MIN_DURATION, clip.mediaDurationSeconds)
        return clip.copy(trimEndSeconds = safeEnd)
    }

    fun splitAtPlayhead(clip: Clip, playheadSeconds: Double): Pair<Clip, Clip>? {
        if (playheadSeconds <= clip.trimStartSeconds + MIN_DURATION) return null
        if (playheadSeconds >= clip.trimEndSeconds - MIN_DURATION) return null

        val first = clip.copy(trimEndSeconds = playheadSeconds)
        val second = clip.copy(
            id = clip.id + "_split",
            trimStartSeconds = playheadSeconds
        )
        return first to second
    }

    companion object {
        const val MIN_DURATION = 0.1
    }
}
