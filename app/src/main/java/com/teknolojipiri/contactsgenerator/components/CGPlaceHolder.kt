package com.teknolojipiri.contactsgenerator.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle


@Composable
fun CGPlaceHolder(text: String) {
    Text(text = text, fontStyle = FontStyle.Italic)
}