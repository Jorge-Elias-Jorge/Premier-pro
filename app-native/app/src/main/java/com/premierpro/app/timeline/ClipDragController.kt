package com.premierpro.app.timeline

class ClipDragController {

    fun reorder(
        clips: List<Clip>,
        fromIndex: Int,
        toIndex: Int
    ): List<Clip> {
        if (fromIndex == toIndex) return clips
        if (fromIndex < 0 || fromIndex >= clips.size) return clips
        if (toIndex < 0 || toIndex >= clips.size) return clips

        val result = clips.toMutableList()
        val clip = result.removeAt(fromIndex)
        result.add(toIndex, clip)
        return result
    }

    fun moveByOffset(
        clips: List<Clip>,
        clipId: String,
        offset: Int
    ): List<Clip> {
        val index = clips.indexOfFirst { it.id == clipId }
        if (index == -1) return clips
        val target = (index + offset).coerceIn(0, clips.size - 1)
        return reorder(clips, index, target)
    }

    fun moveTowards(
        clips: List<Clip>,
        clipId: String,
        direction: Int
    ): List<Clip> {
        return moveByOffset(clips, clipId, direction)
    }
}
