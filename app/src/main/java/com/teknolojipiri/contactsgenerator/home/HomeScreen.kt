package com.teknolojipiri.contactsgenerator.home

import android.Manifest.permission.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.teknolojipiri.contactsgenerator.components.CGButton
import com.teknolojipiri.contactsgenerator.components.CGRowSpacer


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(onGeneratorTapped: () -> Unit, onDeleterTapped: () -> Unit) {

    // Camera permission state
    val permissionState = rememberMultiplePermissionsState(
        listOf(READ_CONTACTS, WRITE_CONTACTS, GET_ACCOUNTS)
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CGButton("Generate Contacts") {
            when (permissionState.permissions.all { it.status.isGranted }) {
                true -> onGeneratorTapped()
                false -> {
                    permissionState.launchMultiplePermissionRequest()
                }
            }
        }
        CGRowSpacer()
        CGButton("Delete Contacts") {
            when (permissionState.permissions.all { it.status.isGranted }) {
                true -> onDeleterTapped()
                false -> {
                    permissionState.launchMultiplePermissionRequest()
                }
            }
        }
    }
}

