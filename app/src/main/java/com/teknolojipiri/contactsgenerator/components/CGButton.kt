package com.teknolojipiri.contactsgenerator.components

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun CGButton(name: String, enabled: Boolean = true, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, enabled = enabled) {
        Text(text = name, fontSize = 28.sp)
    }
}