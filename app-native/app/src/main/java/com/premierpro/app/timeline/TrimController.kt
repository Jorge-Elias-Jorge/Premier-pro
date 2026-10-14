package com.premierpro.app.timeline

class TrimController {

    fun adjustStart(
        clip: Clip,
        newStart: Double,
        useSnap: Boolean = true
    ): Clip {
        val snapped = if (useSnap) {
            SnapEngine.snapTime(newStart, listOf(clip))
        } else {
            newStart
        }
        val safeStart = snapped.coerceIn(0.0, clip.trimEndSeconds - MIN_DURATION)
        return clip.copy(trimStartSeconds = safeStart)
    }

    fun adjustEnd(
        clip: Clip,
        newEnd: Double,
        useSnap: Boolean = true
    ): Clip {
        val snapped = if (useSnap) {
            SnapEngine.snapTime(newEnd, listOf(clip))
        } else {
            newEnd
        }
        val safeEnd = snapped.coerceIn(clip.trimStartSeconds + MIN_DURATION, clip.mediaDurationSeconds)
        return clip.copy(trimEndSeconds = safeEnd)
    }

    fun nudgeStart(clip: Clip, deltaSeconds: Double): Clip {
        return adjustStart(clip, clip.trimStartSeconds + deltaSeconds, useSnap = false)
    }

    fun nudgeEnd(clip: Clip, deltaSeconds: Double): Clip {
        return adjustEnd(clip, clip.trimEndSeconds + deltaSeconds, useSnap = false)
    }

    companion object {
        const val MIN_DURATION = 0.1
    }
}
