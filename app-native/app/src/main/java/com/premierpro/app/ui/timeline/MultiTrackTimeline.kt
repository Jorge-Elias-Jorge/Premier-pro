package com.premierpro.app.ui.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierpro.app.timeline.Clip
import com.premierpro.app.timeline.MultiTrackState
import com.premierpro.app.timeline.Track

@Composable
fun MultiTrackTimeline(
    multiTrackState: MultiTrackState,
    clips: List<Clip>,
    selectedClipId: String?,
    zoom: Float,
    onSelectClip: (String) -> Unit
) {
    val tracks = multiTrackState.tracks

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(Color(0xFF0E0E16))
    ) {
        items(tracks) { track ->
            TrackRow(
                track = track,
                clips = clips.filter { track.clipIds.contains(it.id) },
                selectedClipId = selectedClipId,
                zoom = zoom,
                onSelectClip = onSelectClip
            )
        }
    }
}

@Composable
private fun TrackRow(
    track: Track,
    clips: List<Clip>,
    selectedClipId: String?,
    zoom: Float,
    onSelectClip: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = track.name,
            color = Color(0xFF9A9AA8),
            fontSize = 12.sp,
            modifier = Modifier
                .width(70.dp)
                .align(Alignment.CenterVertically)
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF101018), RoundedCornerShape(8.dp))
                .padding(4.dp)
        ) {
            clips.forEach { clip ->
                ClipBlock(
                    clip = clip,
                    selected = clip.id == selectedClipId,
                    zoom = zoom,
                    onClick = { onSelectClip(clip.id) }
                )
            }
            if (clips.isEmpty()) {
                Text(
                    text = "Faixa vazia",
                    color = Color(0xFF55555F),
                    fontSize = 11.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}
