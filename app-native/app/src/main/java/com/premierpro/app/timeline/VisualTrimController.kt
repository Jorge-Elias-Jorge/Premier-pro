package com.premierpro.app.timeline

class VisualTrimController(
    private val zoomState: ZoomState
) {

    fun trimStartByPixels(
        clip: Clip,
        deltaPixels: Float
    ): Clip {
        val seconds = pixelsToSeconds(deltaPixels)
        val newStart = clip.trimStartSeconds + seconds
        return trimController.adjustStart(clip, newStart, useSnap = false)
    }

    fun trimEndByPixels(
        clip: Clip,
        deltaPixels: Float
    ): Clip {
        val seconds = pixelsToSeconds(deltaPixels)
        val newEnd = clip.trimEndSeconds + seconds
        return trimController.adjustEnd(clip, newEnd, useSnap = false)
    }

    private fun pixelsToSeconds(pixels: Float): Double {
        val zoom = zoomState.zoom
        if (zoom <= 0f) return 0.0
        return (pixels / (ZoomState.BASE_PIXELS_PER_SECOND * zoom)).toDouble()
    }

    private val trimController = TrimController()
}
