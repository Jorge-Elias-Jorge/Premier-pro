package com.premierpro.app.ui.timeline

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.premierpro.app.timeline.Clip
import com.premierpro.app.timeline.ZoomState
import com.premierpro.app.timeline.gestures.timelineDragGestures
import com.premierpro.app.timeline.gestures.timelineZoomGestures
import com.premierpro.app.ui.theme.Accent

@Composable
fun TimelineScreen(
    clips: List<Clip>,
    selectedClipId: String?,
    zoomState: ZoomState,
    onSelectClip: (String) -> Unit,
    onMoveClip: (String, Int) -> Unit,
    onZoom: (Float) -> Unit
) {
    var scrollOffset by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color(0xFF0E0E16))
    ) {
        TimelineToolbar(
            onZoomIn = { onZoom(1.25f) },
            onZoomOut = { onZoom(0.8f) },
            onReset = { onZoom(1f) }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .timelineDragGestures(
                    onDragStart = { },
                    onDrag = { delta -> scrollOffset -= delta },
                    onDragEnd = { }
                )
                .timelineZoomGestures(
                    onZoom = { zoom, _ -> onZoom(zoom) }
                )
        ) {
            TimelineRuler(
                zoom = zoomState.zoom,
                scrollOffset = scrollOffset
            )

            LazyRow(
                modifier = Modifier.fillMaxSize()
            ) {
                items(clips) { clip ->
                    ClipBlock(
                        clip = clip,
                        selected = clip.id == selectedClipId,
                        zoom = zoomState.zoom,
                        onClick = { onSelectClip(clip.id) }
                    )
                }
            }

            Playhead(
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}

@Composable
private fun TimelineToolbar(
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AssistChip(onClick = onZoomIn, label = { Text("Zoom +") })
        AssistChip(onClick = onZoomOut, label = { Text("Zoom -") })
        AssistChip(onClick = onReset, label = { Text("Ajustar") })
    }
}

@Composable
private fun TimelineRuler(
    zoom: Float,
    scrollOffset: Float
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(18.dp)
    ) {
        val secondsSpacing = 60f * zoom
        var x = scrollOffset % secondsSpacing
        while (x < size.width) {
            drawLine(
                color = Color(0xFF3A3A4A),
                start = Offset(x, 0f),
                end = Offset(x, size.height)
            )
            x += secondsSpacing
        }
    }
}

@Composable
private fun Playhead(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .width(2.dp)
            .fillMaxHeight()
    ) {
        drawRect(color = Accent)
    }
}
