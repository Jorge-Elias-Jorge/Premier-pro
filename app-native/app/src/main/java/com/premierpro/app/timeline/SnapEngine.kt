package com.premierpro.app.timeline

object SnapEngine {

    const val SNAP_THRESHOLD_SECONDS = 0.15

    fun snapTime(
        timeSeconds: Double,
        clips: List<Clip>
    ): Double {
        var bestTime = timeSeconds
        var bestDistance = SNAP_THRESHOLD_SECONDS

        for (clip in clips) {
            val candidates = listOf(
                clip.trimStartSeconds,
                clip.trimEndSeconds
            )
            for (candidate in candidates) {
                val distance = kotlin.math.abs(candidate - timeSeconds)
                if (distance < bestDistance) {
                    bestDistance = distance
                    bestTime = candidate
                }
            }
        }
        return bestTime
    }

    fun snapToFrame(timeSeconds: Double, fps: Double = 30.0): Double {
        if (fps <= 0) return timeSeconds
        val frameDuration = 1.0 / fps
        return Math.round(timeSeconds / frameDuration) * frameDuration
    }
}
