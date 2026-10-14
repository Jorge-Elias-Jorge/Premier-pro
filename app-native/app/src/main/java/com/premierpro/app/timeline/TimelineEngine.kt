package com.premierpro.app.timeline

object TimelineEngine {

    fun canSplit(clip: Clip?, playheadSeconds: Double): Boolean {
        if (clip == null) return false
        if (clip.type != ClipType.VIDEO) return false
        return clip.containsPlayhead(playheadSeconds)
    }

    fun findClipAtTime(clips: List<Clip>, timeSeconds: Double): Clip? {
        var elapsed = 0.0
        for (clip in clips) {
            val end = elapsed + clip.durationSeconds
            if (timeSeconds >= elapsed && timeSeconds < end) {
                return clip
            }
            elapsed = end
        }
        return null
    }

    fun projectTimeForClip(clips: List<Clip>, clipId: String): Double {
        var elapsed = 0.0
        for (clip in clips) {
            if (clip.id == clipId) return elapsed
            elapsed += clip.durationSeconds
        }
        return elapsed
    }

    fun normalizeTrim(clip: Clip, start: Double, end: Double): Pair<Double, Double> {
        val safeStart = start.coerceIn(0.0, clip.mediaDurationSeconds)
        val safeEnd = end.coerceIn(safeStart, clip.mediaDurationSeconds)
        return safeStart to safeEnd
    }
}
