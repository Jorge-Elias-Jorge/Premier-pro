package com.premierpro.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.premierpro.app.ui.editor.EditorScreen
import com.premierpro.app.ui.theme.PremierProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PremierProTheme {
                EditorScreen()
            }
        }
    }
}
