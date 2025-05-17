package com.nasdin.hostile1.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    permissions: List<String>,
    onPermissionResult: (Boolean) -> Unit
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)
    var permissionRequested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!permissionRequested) {
            permissionRequested = true
            multiplePermissionsState.launchMultiplePermissionRequest()
        }
    }

    // Trigger once when permission state updates
    LaunchedEffect(multiplePermissionsState.permissions) {
        val allGranted = multiplePermissionsState.permissions.all { it.status.isGranted }
        if (allGranted) {
            onPermissionResult(true)
        } else if (multiplePermissionsState.permissions.any { !it.status.isGranted && !it.status.shouldShowRationale }) {
            onPermissionResult(false)
        }
    }
}