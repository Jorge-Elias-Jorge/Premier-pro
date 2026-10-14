package com.premierpro.app.ui.timeline

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.premierpro.app.timeline.Clip
import com.premierpro.app.timeline.VisualTrimController
import com.premierpro.app.timeline.ZoomState

@Composable
fun TrimHandles(
    clip: Clip,
    zoomState: ZoomState,
    onTrim: (Clip) -> Unit
) {
    val visualTrim = remember { VisualTrimController(zoomState) }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(84.dp)
                .pointerInput(clip.id) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            onTrim(visualTrim.trimStartByPixels(clip, dragAmount.x))
                        }
                    )
                }
        )

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .width(28.dp)
                .height(84.dp)
                .pointerInput(clip.id) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            onTrim(visualTrim.trimEndByPixels(clip, dragAmount.x))
                        }
                    )
                }
        )
    }
}
