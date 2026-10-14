package com.premierpro.app.text

enum class TextAnimation(
    val displayName: String
) {
    NONE("Nenhuma"),
    FADE_IN("Fade in"),
    FADE_OUT("Fade out"),
    SLIDE_UP("Slide up"),
    SLIDE_DOWN("Slide down"),
    POP("Pop")
}

class TextAnimationEngine {

    fun progressAt(
        animation: TextAnimation,
        elapsedSeconds: Double,
        durationSeconds: Double
    ): Float {
        if (durationSeconds <= 0.0) return 1f
        val progress = (elapsedSeconds / durationSeconds).coerceIn(0.0, 1.0)
        return progress.toFloat()
    }

    fun availableAnimations(): List<TextAnimation> {
        return TextAnimation.entries.toList()
    }
}
