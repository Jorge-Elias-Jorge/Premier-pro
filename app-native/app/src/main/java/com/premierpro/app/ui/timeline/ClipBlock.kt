package com.premierpro.app.ui.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierpro.app.timeline.Clip
import com.premierpro.app.ui.theme.Accent

@Composable
fun ClipBlock(
    clip: Clip,
    selected: Boolean,
    zoom: Float,
    onClick: () -> Unit
) {
    val widthDp = (clip.durationSeconds * 60f * zoom).dp

    Column(
        modifier = Modifier
            .width(widthDp.coerceAtLeast(90.dp))
            .height(84.dp)
            .padding(4.dp)
            .background(
                color = if (selected) Accent else Color(0xFF1D1D28),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = clip.name,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "%.1fs - %.1fs".format(clip.trimStartSeconds, clip.trimEndSeconds),
            color = Color(0xFF9A9AA8),
            fontSize = 10.sp
        )
        Text(
            text = clip.type.name,
            color = Color(0xFF9A9AA8),
            fontSize = 10.sp
        )
    }
}
