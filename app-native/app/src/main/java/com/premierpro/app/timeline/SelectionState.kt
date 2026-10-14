package com.premierpro.app.timeline

class SelectionState {
    var selectedClipId: String? = null
        private set

    var playheadSeconds: Double = 0.0
        private set

    fun select(clipId: String?) {
        selectedClipId = clipId
    }

    fun clearSelection() {
        selectedClipId = null
    }

    fun movePlayhead(timeSeconds: Double) {
        playheadSeconds = timeSeconds.coerceAtLeast(0.0)
    }

    fun movePlayheadBy(deltaSeconds: Double) {
        movePlayhead(playheadSeconds + deltaSeconds)
    }
}
