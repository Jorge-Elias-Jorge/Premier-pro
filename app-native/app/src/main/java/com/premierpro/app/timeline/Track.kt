package com.premierpro.app.timeline

data class Track(
    val id: String,
    val name: String,
    val type: TrackType,
    val clipIds: List<String> = emptyList()
)

enum class TrackType {
    VIDEO,
    AUDIO,
    TEXT,
    CAPTION
}
