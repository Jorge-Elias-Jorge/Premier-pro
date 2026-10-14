package com.premierpro.app.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premierpro.app.timeline.Clip
import com.premierpro.app.ui.theme.Accent
import com.premierpro.app.ui.theme.SurfaceHigh

@Composable
fun EditorScreen() {
    val viewModel = remember { EditorViewModel() }
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF08080D))
    ) {
        EditorTopBar(
            onImport = viewModel::openMediaPicker,
            onExportSrt = viewModel::exportSrt
        )

        PreviewArea(
            currentText = state.currentTextOverlay,
            hasMedia = state.clips.isNotEmpty()
        )

        ToolBar(
            canEdit = state.selectedClip != null,
            canSplit = state.canSplit,
            onSplit = viewModel::splitSelected,
            onDuplicate = viewModel::duplicateSelected,
            onDelete = viewModel::deleteSelected,
            onAddText = viewModel::addTextToSelected,
            onAddCaption = viewModel::addCaption,
            onPlay = viewModel::playProject
        )

        ClipPropertiesPanel(
            selectedClip = state.selectedClip,
            onTextChange = viewModel::updateText
        )

        TimelinePanel(
            clips = state.clips,
            selectedClipId = state.selectedClipId,
            onSelect = viewModel::selectClip
        )
    }
}

@Composable
private fun EditorTopBar(
    onImport: () -> Unit,
    onExportSrt: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Premier Pro",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Button(onClick = onImport) {
            Text("Importar")
        }
        Spacer(Modifier.width(8.dp))
        OutlinedButton(onClick = onExportSrt) {
            Text("SRT")
        }
    }
}

@Composable
private fun PreviewArea(
    currentText: String,
    hasMedia: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(horizontal = 12.dp)
            .background(Color.Black, RoundedCornerShape(16.dp))
    ) {
        if (!hasMedia) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Premier Pro 1.0", fontWeight = FontWeight.Bold)
                Text("Importe mídia para começar", color = Color(0xFF9A9AA8))
            }
        }
        if (currentText.isNotBlank()) {
            Text(
                text = currentText,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
            )
        }
    }
}

@Composable
private fun ToolBar(
    canEdit: Boolean,
    canSplit: Boolean,
    onSplit: () -> Unit,
    onDuplicate: () -> Unit,
    onDelete: () -> Unit,
    onAddText: () -> Unit,
    onAddCaption: () -> Unit,
    onPlay: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AssistChip(onClick = onSplit, enabled = canSplit, label = { Text("Dividir") })
        AssistChip(onClick = onDuplicate, enabled = canEdit, label = { Text("Duplicar") })
        AssistChip(onClick = onDelete, enabled = canEdit, label = { Text("Excluir") })
        AssistChip(onClick = onAddText, enabled = canEdit, label = { Text("Texto") })
        AssistChip(onClick = onAddCaption, label = { Text("Legenda") })
        AssistChip(onClick = onPlay, label = { Text("Reproduzir") })
    }
}

@Composable
private fun ClipPropertiesPanel(
    selectedClip: Clip?,
    onTextChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigh)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text("Propriedades do clipe", fontWeight = FontWeight.Bold)
            if (selectedClip == null) {
                Text("Selecione um clipe na timeline")
            } else {
                Text("Início: %.1fs".format(selectedClip.trimStartSeconds))
                Text("Fim: %.1fs".format(selectedClip.trimEndSeconds))
                OutlinedTextField(
                    value = selectedClip.textOverlay,
                    onValueChange = onTextChange,
                    label = { Text("Texto sobre o vídeo") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TimelinePanel(
    clips: List<Clip>,
    selectedClipId: String?,
    onSelect: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(12.dp)
    ) {
        items(clips) { clip ->
            ClipCard(
                clip = clip,
                selected = clip.id == selectedClipId,
                onClick = { onSelect(clip.id) }
            )
        }
    }
}

@Composable
private fun ClipCard(
    clip: Clip,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(140.dp)
            .height(84.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Accent else Color(0xFF1D1D28)
        )
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(clip.name, maxLines = 1, fontWeight = FontWeight.Bold)
            Text("%.1fs - %.1fs".format(clip.trimStartSeconds, clip.trimEndSeconds))
            Text(clip.type.name, color = Color(0xFF9A9AA8))
        }
    }
}
