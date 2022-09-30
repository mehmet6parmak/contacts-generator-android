package com.teknolojipiri.contactsgenerator.remove

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teknolojipiri.contactsgenerator.components.CGButton
import com.teknolojipiri.contactsgenerator.components.CGColumn
import com.teknolojipiri.contactsgenerator.components.CGErrorText
import com.teknolojipiri.contactsgenerator.components.CGRowSpacer
import com.teknolojipiri.contactsgenerator.extensions.rememberFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactRemoverScreen(viewModel: ContactRemoverViewModel = hiltViewModel()) {
    CGColumn {
        val flow = rememberFlow(viewModel.uiState)
        val state by flow.collectAsState(initial = ContactRemoverViewModel.UiState.Success)
        CGButton(name = "Remove All Contacts", enabled = state.buttonEnabled) {
            viewModel.removeAll()
        }

        Divider(modifier = Modifier.padding(vertical = 32.dp), thickness = 1.dp, color = MaterialTheme.colorScheme.primary)

        var prefix by remember { mutableStateOf("") }
        OutlinedTextField(value = prefix,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            onValueChange = { prefix = it },
            label = {
                Text(text = "Prefix")
            })

        var rangeStart by remember { mutableStateOf("") }
        OutlinedTextField(value = rangeStart,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = { rangeStart = it },
            label = {
                Text(text = "Range Start")
            })

        var rangeEnd by remember { mutableStateOf("") }
        OutlinedTextField(value = rangeEnd,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            onValueChange = { rangeEnd = it },
            label = {
                Text(text = "Range End")
            })

        CGRowSpacer()
        CGRowSpacer()

        OutlinedButton(enabled = state.buttonEnabled, onClick = {
            viewModel.deleteContacts(prefix, rangeStart, rangeEnd)
        }) {
            Text(text = "Remove Range", fontSize = 28.sp)
        }

        when (state) {
            is ContactRemoverViewModel.UiState.Failure -> {
                CGRowSpacer()
                CGErrorText(
                    text = (state as ContactRemoverViewModel.UiState.Failure).message
                        ?: "Operation failed, please try again after clearing possible mass."
                )
            }
            else -> {}
        }
    }
}