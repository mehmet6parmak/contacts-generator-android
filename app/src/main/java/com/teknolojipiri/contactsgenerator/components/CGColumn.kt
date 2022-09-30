package com.teknolojipiri.contactsgenerator.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CGColumn(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}