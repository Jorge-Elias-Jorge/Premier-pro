package com.premierpro.app.ui.export

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.premierpro.app.export.ExportProgress

@Composable
fun ExportScreen() {
    val progress = remember { ExportProgress() }
    var progressValue by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Exportar projeto")

        Spacer(Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = { progressValue },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                progress.start(total = 100)
                progressValue = progress.progress()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar exportação")
        }
    }
}
