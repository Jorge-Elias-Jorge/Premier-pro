package com.premierpro.app.effects

data class ColorAdjustments(
    val brightness: Float = 0f,
    val contrast: Float = 1f,
    val saturation: Float = 1f,
    val temperature: Float = 0f,
    val tint: Float = 0f,
    val exposure: Float = 0f
) {

    fun isValid(): Boolean {
        return brightness in -1f..1f &&
            contrast in 0f..3f &&
            saturation in 0f..3f &&
            temperature in -1f..1f &&
            tint in -1f..1f &&
            exposure in -2f..2f
    }

    fun reset(): ColorAdjustments {
        return ColorAdjustments()
    }

    fun adjusted(): ColorAdjustments {
        return copy(
            brightness = brightness.coerceIn(-1f, 1f),
            contrast = contrast.coerceIn(0f, 3f),
            saturation = saturation.coerceIn(0f, 3f),
            temperature = temperature.coerceIn(-1f, 1f),
            tint = tint.coerceIn(-1f, 1f),
            exposure = exposure.coerceIn(-2f, 2f)
        )
    }
}
