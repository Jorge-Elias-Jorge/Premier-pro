package com.premierpro.app.timeline

import android.net.Uri

class TimelineState {
    private val _clips = mutableListOf<Clip>()
    val clips: List<Clip>
        get() = _clips.toList()

    var selectedClipId: String? = null
        private set

    val selectedClip: Clip?
        get() = _clips.firstOrNull { it.id == selectedClipId }

    val totalDurationSeconds: Double
        get() = _clips.sumOf { it.durationSeconds }

    fun addClip(
        name: String,
        type: ClipType,
        uri: Uri,
        durationSeconds: Double
    ): Clip {
        val clip = Clip(
            id = generateClipId(),
            name = name,
            type = type,
            uri = uri,
            mediaDurationSeconds = durationSeconds,
            trimEndSeconds = if (type == ClipType.IMAGE) 5.0 else durationSeconds
        )
        _clips.add(clip)
        selectedClipId = clip.id
        return clip
    }

    fun select(clipId: String?) {
        selectedClipId = _clips.firstOrNull { it.id == clipId }?.id
    }

    fun splitSelected(atTimeSeconds: Double): Boolean {
        val index = _clips.indexOfFirst { it.id == selectedClipId }
        if (index == -1) return false
        val original = _clips[index]
        val parts = original.splitAt(atTimeSeconds) ?: return false
        _clips[index] = parts.first
        _clips.add(index + 1, parts.second)
        selectedClipId = parts.second.id
        return true
    }

    fun deleteSelected(): Boolean {
        val removed = _clips.removeIf { it.id == selectedClipId }
        if (removed) selectedClipId = null
        return removed
    }

    fun duplicateSelected(): Boolean {
        val clip = selectedClip ?: return false
        val index = _clips.indexOfFirst { it.id == clip.id }
        val copy = clip.copy(id = generateClipId())
        _clips.add(index + 1, copy)
        selectedClipId = copy.id
        return true
    }

    fun moveSelectedUp(): Boolean = moveSelected(-1)

    fun moveSelectedDown(): Boolean = moveSelected(1)

    private fun moveSelected(direction: Int): Boolean {
        val index = _clips.indexOfFirst { it.id == selectedClipId }
        if (index == -1) return false
        val target = index + direction
        if (target < 0 || target >= _clips.size) return false
        val clip = _clips.removeAt(index)
        _clips.add(target, clip)
        return true
    }

    fun updateSelectedTrim(startSeconds: Double, endSeconds: Double): Boolean {
        val index = _clips.indexOfFirst { it.id == selectedClipId }
        if (index == -1) return false
        val clip = _clips[index]
        if (startSeconds < 0 || endSeconds > clip.mediaDurationSeconds) return false
        if (endSeconds <= startSeconds) return false
        _clips[index] = clip.copy(
            trimStartSeconds = startSeconds,
            trimEndSeconds = endSeconds
        )
        return true
    }

    fun updateSelectedText(text: String): Boolean {
        val index = _clips.indexOfFirst { it.id == selectedClipId }
        if (index == -1) return false
        _clips[index] = _clips[index].copy(textOverlay = text)
        return true
    }

    fun clear() {
        _clips.clear()
        selectedClipId = null
    }

    companion object {
        fun generateClipId(): String {
            return "clip_" + System.currentTimeMillis().toString(36) + "_" + (0..9999).random()
        }
    }
}
