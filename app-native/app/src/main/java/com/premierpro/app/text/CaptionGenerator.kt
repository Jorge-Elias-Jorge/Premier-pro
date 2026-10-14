package com.premierpro.app.text

data class CaptionSegment(
    val text: String,
    val startSeconds: Double,
    val endSeconds: Double
) {

    fun duration(): Double {
        return endSeconds - startSeconds
    }

    fun isValid(): Boolean {
        return text.isNotBlank() && endSeconds > startSeconds
    }
}

class CaptionGenerator {

    fun generateFromTranscript(
        transcript: List<Pair<String, Pair<Double, Double>>>
    ): List<CaptionSegment> {
        return transcript.mapNotNull { (text, timing) ->
            val segment = CaptionSegment(
                text = text,
                startSeconds = timing.first,
                endSeconds = timing.second
            )
            if (segment.isValid()) segment else null
        }
    }

    fun splitLongCaption(
        segment: CaptionSegment,
        maxChars: Int
    ): List<CaptionSegment> {
        if (segment.text.length <= maxChars) return listOf(segment)

        val words = segment.text.split(" ")
        val chunks = mutableListOf<String>()
        var current = StringBuilder()

        for (word in words) {
            if (current.length + word.length + 1 > maxChars) {
                chunks.add(current.toString().trim())
                current = StringBuilder()
            }
            if (current.isNotEmpty()) current.append(" ")
            current.append(word)
        }
        if (current.isNotEmpty()) chunks.add(current.toString().trim())

        val duration = segment.duration()
        val chunkDuration = duration / chunks.size

        return chunks.mapIndexed { index, chunk ->
            CaptionSegment(
                text = chunk,
                startSeconds = segment.startSeconds + index * chunkDuration,
                endSeconds = segment.startSeconds + (index + 1) * chunkDuration
            )
        }
    }

    fun captionAtTime(
        captions: List<CaptionSegment>,
        timeSeconds: Double
    ): CaptionSegment? {
        return captions.firstOrNull {
            timeSeconds >= it.startSeconds && timeSeconds < it.endSeconds
        }
    }
}
