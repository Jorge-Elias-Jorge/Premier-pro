package com.premierpro.app.ui.editor

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.premierpro.app.timeline.ContinuousZoomController
import com.premierpro.app.timeline.MultiTrackState
import com.premierpro.app.timeline.ZoomState
import com.premierpro.app.ui.timeline.MultiTrackTimeline

@Composable
fun EditorIntegration() {
    val zoomState = remember { ZoomState() }
    val multiTrackState = remember { MultiTrackState() }
    val zoomController = remember { ContinuousZoomController(zoomState) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        MultiTrackTimeline(
            multiTrackState = multiTrackState,
            clips = emptyList(),
            selectedClipId = null,
            zoom = zoomState.zoom,
            onSelectClip = { }
        )
    }
}
