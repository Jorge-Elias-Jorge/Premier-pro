package com.premierpro.app.export

data class ExportSettings(
    val resolution: ExportResolution = ExportResolution.HD_1080P,
    val quality: ExportQuality = ExportQuality.HIGH,
    val frameRate: Int = 30,
    val format: ExportFormat = ExportFormat.MP4,
    val includeAudio: Boolean = true
) {

    fun isValid(): Boolean {
        return frameRate in 24..60
    }

    fun normalized(): ExportSettings {
        return copy(frameRate = frameRate.coerceIn(24, 60))
    }
}

enum class ExportResolution(
    val displayName: String,
    val width: Int,
    val height: Int
) {
    SD_480P("480p", 854, 480),
    HD_720P("720p", 1280, 720),
    HD_1080P("1080p", 1920, 1080),
    UHD_4K("4K", 3840, 2160)
}

enum class ExportQuality(
    val displayName: String,
    val bitrateMultiplier: Float
) {
    LOW("Baixa", 0.6f),
    MEDIUM("Média", 1f),
    HIGH("Alta", 1.5f),
    ULTRA("Ultra", 2f)
}

enum class ExportFormat(
    val displayName: String,
    val extension: String
) {
    MP4("MP4", "mp4"),
    WEBM("WebM", "webm")
}
