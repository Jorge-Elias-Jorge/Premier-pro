package com.premierpro.app.timeline.gestures

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.Modifier

fun Modifier.timelineDragGestures(
    onDragStart: () -> Unit,
    onDrag: (Float) -> Unit,
    onDragEnd: () -> Unit
): Modifier = pointerInput(Unit) {
    detectDragGestures(
        onDragStart = { onDragStart() },
        onDrag = { change, dragAmount ->
            change.consume()
            onDrag(dragAmount.x)
        },
        onDragEnd = { onDragEnd() },
        onDragCancel = { onDragEnd() }
    )
}

fun Modifier.timelineZoomGestures(
    onZoom: (Float, Offset) -> Unit
): Modifier = pointerInput(Unit) {
    detectTransformGestures { _, pan, zoom, _ ->
        if (zoom != 1f) {
            onZoom(zoom, pan)
        }
    }
}
