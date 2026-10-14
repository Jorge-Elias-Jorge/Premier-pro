package com.premierpro.app.effects

enum class TransitionType(
    val displayName: String
) {
    NONE("Nenhuma"),
    FADE("Fade"),
    CROSSFADE("Crossfade"),
    SLIDE("Slide"),
    WIPE("Wipe"),
    ZOOM("Zoom")
}

class TransitionEngine {

    fun applyTransition(
        type: TransitionType,
        durationSeconds: Double
    ): Transition {
        val safeDuration = durationSeconds.coerceIn(MIN_DURATION, MAX_DURATION)
        return Transition(
            type = type,
            durationSeconds = safeDuration
        )
    }

    fun progressAt(
        transition: Transition,
        elapsedSeconds: Double
    ): Float {
        if (transition.durationSeconds <= 0.0) return 1f
        val progress = (elapsedSeconds / transition.durationSeconds)
            .coerceIn(0.0, 1.0)
        return progress.toFloat()
    }

    companion object {
        const val MIN_DURATION = 0.1
        const val MAX_DURATION = 5.0
    }
}

data class Transition(
    val type: TransitionType,
    val durationSeconds: Double
)
