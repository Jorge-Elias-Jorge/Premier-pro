package com.premierpro.app.captions

data class Caption(
    val id: Long,
    val startSeconds: Double,
    val endSeconds: Double,
    val text: String
)

object SrtExporter {

    fun export(captions: List<Caption>): String {
        return captions
            .sortedBy { it.startSeconds }
            .mapIndexed { index, caption ->
                buildString {
                    append(index + 1)
                    append("\n")
                    append(formatTime(caption.startSeconds))
                    append(" --> ")
                    append(formatTime(caption.endSeconds))
                    append("\n")
                    append(caption.text)
                    append("\n")
                }
            }
            .joinToString("\n")
    }

    private fun formatTime(seconds: Double): String {
        val safeSeconds = seconds.coerceAtLeast(0.0)
        val totalMilliseconds = (safeSeconds * 1000).toLong()
        val hours = totalMilliseconds / 3_600_000
        val minutes = (totalMilliseconds / 60_000) % 60
        val secs = (totalMilliseconds / 1000) % 60
        val millis = totalMilliseconds % 1000
        return "%02d:%02d:%02d,%03d".format(hours, minutes, secs, millis)
    }
}
