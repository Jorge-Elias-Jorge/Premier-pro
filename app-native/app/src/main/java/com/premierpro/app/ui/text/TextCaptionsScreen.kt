package com.premierpro.app.ui.text

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.premierpro.app.text.TextEditor

@Composable
fun TextCaptionsScreen() {
    val editor = remember { TextEditor() }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Texto e legendas")

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Digite o texto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                editor.createText(
                    id = "text_${System.currentTimeMillis()}",
                    content = content,
                    startSeconds = 0.0,
                    endSeconds = 3.0
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar texto")
        }
    }
}
