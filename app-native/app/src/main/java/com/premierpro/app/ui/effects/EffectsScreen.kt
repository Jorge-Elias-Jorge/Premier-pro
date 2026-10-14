package com.premierpro.app.ui.effects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.premierpro.app.effects.VisualFilter
import com.premierpro.app.effects.VisualFilterEngine

@Composable
fun EffectsScreen() {
    val engine = remember { VisualFilterEngine() }
    var selected by remember { mutableStateOf(VisualFilter.NONE) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Filtros")

        Spacer(Modifier.height(12.dp))

        LazyRow {
            items(engine.availableFilters()) { filter ->
                Button(
                    onClick = { selected = filter },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(filter.displayName)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Selecionado: ${selected.displayName}")
    }
}
