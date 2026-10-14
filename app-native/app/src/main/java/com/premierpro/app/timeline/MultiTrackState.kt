package com.premierpro.app.timeline

class MultiTrackState {

    private val _tracks = mutableListOf(
        Track(id = "track_video", name = "Vídeo", type = TrackType.VIDEO),
        Track(id = "track_audio", name = "Áudio", type = TrackType.AUDIO),
        Track(id = "track_text", name = "Texto", type = TrackType.TEXT),
        Track(id = "track_caption", name = "Legendas", type = TrackType.CAPTION)
    )

    val tracks: List<Track>
        get() = _tracks.toList()

    fun addClipToTrack(trackId: String, clipId: String): Boolean {
        val index = _tracks.indexOfFirst { it.id == trackId }
        if (index == -1) return false
        val track = _tracks[index]
        if (track.clipIds.contains(clipId)) return false
        _tracks[index] = track.copy(clipIds = track.clipIds + clipId)
        return true
    }

    fun removeClipFromTrack(clipId: String): Boolean {
        var removed = false
        for (index in _tracks.indices) {
            val track = _tracks[index]
            if (track.clipIds.contains(clipId)) {
                _tracks[index] = track.copy(clipIds = track.clipIds - clipId)
                removed = true
            }
        }
        return removed
    }

    fun moveClipBetweenTracks(
        clipId: String,
        fromTrackId: String,
        toTrackId: String
    ): Boolean {
        val fromIndex = _tracks.indexOfFirst { it.id == fromTrackId }
        val toIndex = _tracks.indexOfFirst { it.id == toTrackId }
        if (fromIndex == -1 || toIndex == -1) return false

        val fromTrack = _tracks[fromIndex]
        if (!fromTrack.clipIds.contains(clipId)) return false

        _tracks[fromIndex] = fromTrack.copy(clipIds = fromTrack.clipIds - clipId)
        val toTrack = _tracks[toIndex]
        _tracks[toIndex] = toTrack.copy(clipIds = toTrack.clipIds + clipId)
        return true
    }

    fun findTrackOfClip(clipId: String): Track? {
        return _tracks.firstOrNull { it.clipIds.contains(clipId) }
    }

    fun clear() {
        for (index in _tracks.indices) {
            _tracks[index] = _tracks[index].copy(clipIds = emptyList())
        }
    }
}
