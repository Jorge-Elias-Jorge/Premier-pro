package com.premierpro.app.effects

enum class VisualFilter(
    val displayName: String
) {
    NONE("Nenhum"),
    CINEMATIC("Cinematográfico"),
    VINTAGE("Vintage"),
    BLACK_AND_WHITE("Preto e branco"),
    WARM("Quente"),
    COOL("Frio"),
    VIVID("Vívido"),
    FADED("Desbotado"),
    DRAMATIC("Dramático"),
    SOFT("Suave")
}

class VisualFilterEngine {

    fun applyFilter(
        base: ColorAdjustments,
        filter: VisualFilter
    ): ColorAdjustments {
        return when (filter) {
            VisualFilter.NONE -> base
            VisualFilter.CINEMATIC -> base.copy(
                contrast = base.contrast * 1.15f,
                saturation = base.saturation * 0.9f,
                temperature = base.temperature + 0.05f
            )
            VisualFilter.VINTAGE -> base.copy(
                saturation = base.saturation * 0.75f,
                temperature = base.temperature + 0.2f,
                exposure = base.exposure + 0.1f
            )
            VisualFilter.BLACK_AND_WHITE -> base.copy(saturation = 0f)
            VisualFilter.WARM -> base.copy(temperature = base.temperature + 0.3f)
            VisualFilter.COOL -> base.copy(temperature = base.temperature - 0.3f)
            VisualFilter.VIVID -> base.copy(
                saturation = base.saturation * 1.4f,
                contrast = base.contrast * 1.1f
            )
            VisualFilter.FADED -> base.copy(
                contrast = base.contrast * 0.85f,
                saturation = base.saturation * 0.8f
            )
            VisualFilter.DRAMATIC -> base.copy(
                contrast = base.contrast * 1.35f,
                exposure = base.exposure - 0.1f
            )
            VisualFilter.SOFT -> base.copy(
                contrast = base.contrast * 0.9f,
                exposure = base.exposure + 0.15f
            )
        }.adjusted()
    }

    fun availableFilters(): List<VisualFilter> {
        return VisualFilter.entries.toList()
    }
}
