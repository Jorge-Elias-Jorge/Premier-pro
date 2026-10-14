package com.premierpro.app.timeline

class ContinuousZoomController(
    private val zoomState: ZoomState
) {

    fun applyZoom(
        currentZoom: Float,
        factor: Float,
        anchorSeconds: Double
    ): Float {
        val newZoom = (currentZoom * factor)
            .coerceIn(ZoomState.MIN_ZOOM, ZoomState.MAX_ZOOM)

        zoomState.zoom = newZoom
        anchorSeconds.coerceAtLeast(0.0)
        return newZoom
    }

    fun pixelsForSeconds(seconds: Double, zoom: Float): Float {
        return (seconds * ZoomState.BASE_PIXELS_PER_SECOND * zoom).toFloat()
    }

    fun secondsForPixels(pixels: Float, zoom: Float): Double {
        if (zoom <= 0f) return 0.0
        return (pixels / (ZoomState.BASE_PIXELS_PER_SECOND * zoom)).toDouble()
    }
}
