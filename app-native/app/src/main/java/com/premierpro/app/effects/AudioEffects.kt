package com.premierpro.app.effects

data class AudioEffects(
    val volume: Float = 1f,
    val fadeInSeconds: Double = 0.0,
    val fadeOutSeconds: Double = 0.0,
    val bassBoost: Float = 0f,
    val trebleBoost: Float = 0f,
    val noiseReduction: Float = 0f
) {

    fun isValid(): Boolean {
        return volume in 0f..2f &&
            fadeInSeconds >= 0.0 &&
            fadeOutSeconds >= 0.0 &&
            bassBoost in -1f..1f &&
            trebleBoost in -1f..1f &&
            noiseReduction in 0f..1f
    }

    fun normalized(): AudioEffects {
        return copy(
            volume = volume.coerceIn(0f, 2f),
            fadeInSeconds = fadeInSeconds.coerceAtLeast(0.0),
            fadeOutSeconds = fadeOutSeconds.coerceAtLeast(0.0),
            bassBoost = bassBoost.coerceIn(-1f, 1f),
            trebleBoost = trebleBoost.coerceIn(-1f, 1f),
            noiseReduction = noiseReduction.coerceIn(0f, 1f)
        )
    }
}

class AudioEffectsEngine {

    fun applyFadeIn(
        effects: AudioEffects,
        seconds: Double
    ): AudioEffects {
        return effects.copy(fadeInSeconds = seconds.coerceAtLeast(0.0)).normalized()
    }

    fun applyFadeOut(
        effects: AudioEffects,
        seconds: Double
    ): AudioEffects {
        return effects.copy(fadeOutSeconds = seconds.coerceAtLeast(0.0)).normalized()
    }

    fun mute(): AudioEffects {
        return AudioEffects(volume = 0f)
    }

    fun reset(): AudioEffects {
        return AudioEffects()
    }
}
