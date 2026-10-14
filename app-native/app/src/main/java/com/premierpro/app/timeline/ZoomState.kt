package com.premierpro.app.timeline

class ZoomState(
    initialZoom: Float = 1f
) {
    var zoom: Float = initialZoom
        private set

    val pixelsPerSecond: Float
        get() = BASE_PIXELS_PER_SECOND * zoom

    fun zoomIn(factor: Float) {
        zoom = (zoom * factor).coerceIn(MIN_ZOOM, MAX_ZOOM)
    }

    fun zoomOut(factor: Float) {
        zoom = (zoom / factor).coerceIn(MIN_ZOOM, MAX_ZOOM)
    }

    fun reset() {
        zoom = 1f
    }

    companion object {
        const val BASE_PIXELS_PER_SECOND = 60f
        const val MIN_ZOOM = 0.25f
        const val MAX_ZOOM = 8f
    }
}
