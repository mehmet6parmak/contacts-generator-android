package com.teknolojipiri.contactsgenerator.generate

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teknolojipiri.contactsgenerator.components.*
import com.teknolojipiri.contactsgenerator.extensions.rememberFlow
import com.teknolojipiri.contactsgenerator.extensions.rememberFlowWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactGeneratorScreen(viewModel: ContactGeneratorViewModel = hiltViewModel()) {
    CGColumn {

        var prefix by remember { mutableStateOf("") }
        OutlinedTextField(
            value = prefix,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            onValueChange = { prefix = it },
            label = {
                Text(text = "Prefix")
            },
            placeholder = {
                CGPlaceHolder(text = "+90532000")
            }
        )

        var rangeStart by remember { mutableStateOf("") }
        OutlinedTextField(
            value = rangeStart,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = { rangeStart = it },
            label = {
                Text(text = "Range Start")
            },
            placeholder = {
                CGPlaceHolder(text = "1000")
            })

        var rangeEnd by remember { mutableStateOf("") }
        OutlinedTextField(
            value = rangeEnd,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            onValueChange = { rangeEnd = it },
            label = {
                Text(text = "Range End")
            },
            placeholder = {
                CGPlaceHolder(text = "1999")
            }
        )

        CGRowSpacer()

        CGInfoText(text = "Make sure range start and range end values have the same amount of digits. Also range values cannot start with 0s.")

        CGRowSpacer()

        val flow = rememberFlow(viewModel.operationResult)
        val state by flow.collectAsState(initial = ContactGeneratorViewModel.Result.Success())
        OutlinedButton(enabled = state.buttonEnabled, onClick = { viewModel.generateContacts(prefix, rangeStart, rangeEnd) }) {
            Text(text = "Generate", fontSize = 28.sp)
        }

        when (state) {
            is ContactGeneratorViewModel.Result.Failed -> {
                CGRowSpacer()
                CGErrorText(text = "Operation failed, please try again after clearing possible mass.")
            }
            else -> {}
        }
    }
}