package com.teknolojipiri.contactsgenerator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CGErrorText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSecondary,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.error)
            .padding(16.dp)
    )
}

@Preview
@Composable
fun PreviewCGErrorText() {
    CGErrorText(text = "Hello World")
}