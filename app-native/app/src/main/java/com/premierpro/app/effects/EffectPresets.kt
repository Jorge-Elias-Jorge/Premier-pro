package com.premierpro.app.effects

data class EffectPreset(
    val id: String,
    val name: String,
    val filter: VisualFilter,
    val color: ColorAdjustments,
    val audio: AudioEffects
)

class EffectPresets {

    fun defaults(): List<EffectPreset> {
        return listOf(
            EffectPreset(
                id = "preset_cinematic",
                name = "Cinematográfico",
                filter = VisualFilter.CINEMATIC,
                color = ColorAdjustments(contrast = 1.2f, saturation = 0.95f),
                audio = AudioEffects(volume = 1f)
            ),
            EffectPreset(
                id = "preset_vintage",
                name = "Vintage",
                filter = VisualFilter.VINTAGE,
                color = ColorAdjustments(temperature = 0.25f, saturation = 0.8f),
                audio = AudioEffects(volume = 1f)
            ),
            EffectPreset(
                id = "preset_dramatic",
                name = "Dramático",
                filter = VisualFilter.DRAMATIC,
                color = ColorAdjustments(contrast = 1.4f, exposure = -0.1f),
                audio = AudioEffects(volume = 1.1f)
            ),
            EffectPreset(
                id = "preset_soft",
                name = "Suave",
                filter = VisualFilter.SOFT,
                color = ColorAdjustments(contrast = 0.9f, exposure = 0.15f),
                audio = AudioEffects(volume = 0.9f)
            )
        )
    }

    fun findById(id: String): EffectPreset? {
        return defaults().firstOrNull { it.id == id }
    }
}
