package com.premierpro.app.ui.timeline

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.premierpro.app.timeline.Clip
import com.premierpro.app.timeline.ClipDragController

@Composable
fun InteractiveTimeline(
    clips: List<Clip>,
    selectedClipId: String?,
    zoom: Float,
    onReorder: (List<Clip>) -> Unit,
    onTrim: (Clip) -> Unit,
    onSelect: (String) -> Unit
) {
    val dragController = remember { ClipDragController() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        clips.forEachIndexed { index, clip ->
            var accumulatedDrag by remember(clip.id) { mutableStateOf(0f) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .pointerInput(clip.id) {
                        detectDragGestures(
                            onDragStart = { accumulatedDrag = 0f },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                accumulatedDrag += dragAmount.x

                                if (accumulatedDrag > TimelineConstants.DRAG_THRESHOLD_PX) {
                                    val reordered = dragController.moveTowards(
                                        clips = clips,
                                        clipId = clip.id,
                                        direction = 1
                                    )
                                    onReorder(reordered)
                                    accumulatedDrag = 0f
                                } else if (accumulatedDrag < -TimelineConstants.DRAG_THRESHOLD_PX) {
                                    val reordered = dragController.moveTowards(
                                        clips = clips,
                                        clipId = clip.id,
                                        direction = -1
                                    )
                                    onReorder(reordered)
                                    accumulatedDrag = 0f
                                }
                            },
                            onDragEnd = { accumulatedDrag = 0f }
                        )
                    }
            ) {
                ClipBlock(
                    clip = clip,
                    selected = clip.id == selectedClipId,
                    zoom = zoom,
                    onClick = { onSelect(clip.id) }
                )
            }
        }
    }
}
