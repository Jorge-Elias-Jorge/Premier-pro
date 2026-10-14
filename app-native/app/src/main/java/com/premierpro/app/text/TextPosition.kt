package com.premierpro.app.text

enum class TextPosition(
    val displayName: String,
    val x: Float,
    val y: Float
) {
    TOP_LEFT("Superior esquerda", 0.1f, 0.1f),
    TOP_CENTER("Superior centro", 0.5f, 0.1f),
    TOP_RIGHT("Superior direita", 0.9f, 0.1f),
    CENTER("Centro", 0.5f, 0.5f),
    BOTTOM_LEFT("Inferior esquerda", 0.1f, 0.9f),
    BOTTOM_CENTER("Inferior centro", 0.5f, 0.9f),
    BOTTOM_RIGHT("Inferior direita", 0.9f, 0.9f)
}

class TextPositionController {

    fun clamp(x: Float, y: Float): Pair<Float, Float> {
        return x.coerceIn(0f, 1f) to y.coerceIn(0f, 1f)
    }

    fun fromPosition(position: TextPosition): Pair<Float, Float> {
        return position.x to position.y
    }
}
