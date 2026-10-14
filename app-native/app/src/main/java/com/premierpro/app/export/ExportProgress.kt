package com.premierpro.app.export

class ExportProgress {

    var renderedFrames: Int = 0
        private set

    var totalFrames: Int = 0
        private set

    var isCancelled: Boolean = false
        private set

    fun start(total: Int) {
        totalFrames = total.coerceAtLeast(0)
        renderedFrames = 0
        isCancelled = false
    }

    fun advance(frames: Int = 1) {
        if (isCancelled) return
        renderedFrames = (renderedFrames + frames)
            .coerceAtMost(totalFrames)
    }

    fun progress(): Float {
        if (totalFrames <= 0) return 1f
        return renderedFrames.toFloat() / totalFrames
    }

    fun cancel() {
        isCancelled = true
    }

    fun isComplete(): Boolean {
        return !isCancelled && totalFrames > 0 && renderedFrames >= totalFrames
    }
}
