package com.premierpro.app.ui.editor

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.premierpro.app.captions.Caption
import com.premierpro.app.timeline.Clip
import com.premierpro.app.timeline.ClipType
import com.premierpro.app.timeline.TimelineEngine
import com.premierpro.app.timeline.TimelineState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EditorUiState(
    val clips: List<Clip> = emptyList(),
    val selectedClip: Clip? = null,
    val selectedClipId: String? = null,
    val currentTextOverlay: String = "",
    val canSplit: Boolean = false,
    val captions: List<Caption> = emptyList()
)

class EditorViewModel : ViewModel() {

    private val timeline = TimelineState()
    private val captions = mutableListOf<Caption>()

    private val _state = MutableStateFlow(EditorUiState())
    val state: StateFlow<EditorUiState> = _state.asStateFlow()

    fun openMediaPicker() {
        // Conectado ao Activity Result API no Pacote 3.
    }

    fun importMedia(name: String, type: ClipType, uri: Uri, durationSeconds: Double) {
        timeline.addClip(name, type, uri, durationSeconds)
        refresh()
    }

    fun selectClip(clipId: String) {
        timeline.select(clipId)
        refresh()
    }

    fun splitSelected() {
        val clip = timeline.selectedClip ?: return
        val playhead = 0.0
        if (TimelineEngine.canSplit(clip, playhead)) {
            timeline.splitSelected(playhead)
        }
        refresh()
    }

    fun duplicateSelected() {
        timeline.duplicateSelected()
        refresh()
    }

    fun deleteSelected() {
        timeline.deleteSelected()
        refresh()
    }

    fun addTextToSelected() {
        timeline.updateSelectedText("Novo texto")
        refresh()
    }

    fun updateTrim(start: Double, end: Double) {
        timeline.updateSelectedTrim(start, end)
        refresh()
    }

    fun updateText(text: String) {
        timeline.updateSelectedText(text)
        refresh()
    }

    fun addCaption() {
        val clip = timeline.selectedClip
        val start = clip?.trimStartSeconds ?: 0.0
        val end = clip?.trimEndSeconds ?: (start + 3.0)
        captions.add(
            Caption(
                id = captions.size + 1L,
                startSeconds = start,
                endSeconds = end,
                text = "Nova legenda"
            )
        )
        refresh()
    }

    fun exportSrt(): String {
        return com.premierpro.app.captions.SrtExporter.export(captions)
    }

    fun playProject() {
        // Reprodução sequencial completa no Pacote 3.
    }

    private fun refresh() {
        val selected = timeline.selectedClip
        _state.value = EditorUiState(
            clips = timeline.clips,
            selectedClip = selected,
            selectedClipId = timeline.selectedClipId,
            currentTextOverlay = selected?.textOverlay ?: "",
            canSplit = TimelineEngine.canSplit(selected, 0.0),
            captions = captions.toList()
        )
    }
}
