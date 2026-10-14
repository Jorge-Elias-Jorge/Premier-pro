package com.premierpro.app.text

data class CaptionStyle(
    val fontSize: Int = 24,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val colorHex: String = "#FFFFFF",
    val backgroundHex: String = "#00000000",
    val outlineHex: String = "#000000",
    val outlineWidth: Float = 0f
) {

    fun isValid(): Boolean {
        return fontSize in 8..120 &&
            colorHex.startsWith("#") &&
            backgroundHex.startsWith("#") &&
            outlineHex.startsWith("#") &&
            outlineWidth >= 0f
    }

    fun normalized(): CaptionStyle {
        return copy(
            fontSize = fontSize.coerceIn(8, 120),
            outlineWidth = outlineWidth.coerceAtLeast(0f)
        )
    }
}

class CaptionStylePresets {

    fun defaults(): List<CaptionStyle> {
        return listOf(
            CaptionStyle(fontSize = 24, bold = true, colorHex = "#FFFFFF"),
            CaptionStyle(fontSize = 28, bold = true, colorHex = "#FFD700", outlineWidth = 2f),
            CaptionStyle(fontSize = 20, italic = true, colorHex = "#FFFFFF", backgroundHex = "#80000000"),
            CaptionStyle(fontSize = 32, bold = true, colorHex = "#FF3B30", outlineWidth = 3f)
        )
    }
}
