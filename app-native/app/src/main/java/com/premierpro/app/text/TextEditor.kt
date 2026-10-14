package com.premierpro.app.text

data class TextClip(
    val id: String,
    val content: String,
    val startSeconds: Double,
    val endSeconds: Double,
    val style: CaptionStyle = CaptionStyle(),
    val position: TextPosition = TextPosition.CENTER,
    val animation: TextAnimation = TextAnimation.NONE
) {

    fun duration(): Double {
        return endSeconds - startSeconds
    }

    fun isValid(): Boolean {
        return content.isNotBlank() &&
            startSeconds >= 0.0 &&
            endSeconds > startSeconds
    }
}

class TextEditor {

    fun createText(
        id: String,
        content: String,
        startSeconds: Double,
        endSeconds: Double
    ): TextClip {
        return TextClip(
            id = id,
            content = content,
            startSeconds = startSeconds,
            endSeconds = endSeconds
        )
    }

    fun updateContent(
        clip: TextClip,
        newContent: String
    ): TextClip {
        return clip.copy(content = newContent)
    }

    fun updateTiming(
        clip: TextClip,
        startSeconds: Double,
        endSeconds: Double
    ): TextClip {
        val safeStart = startSeconds.coerceAtLeast(0.0)
        val safeEnd = endSeconds.coerceAtLeast(safeStart + MIN_DURATION)
        return clip.copy(startSeconds = safeStart, endSeconds = safeEnd)
    }

    fun updateStyle(
        clip: TextClip,
        style: CaptionStyle
    ): TextClip {
        return clip.copy(style = style)
    }

    fun updatePosition(
        clip: TextClip,
        position: TextPosition
    ): TextClip {
        return clip.copy(position = position)
    }

    fun updateAnimation(
        clip: TextClip,
        animation: TextAnimation
    ): TextClip {
        return clip.copy(animation = animation)
    }

    companion object {
        const val MIN_DURATION = 0.2
    }
}
