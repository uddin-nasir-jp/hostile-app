package com.hostile.view

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.nasdin.hostile1.ui.components.RectButton
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CameraView(
    navController: NavController,
    onImageCaptured: (Uri) -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    var imageCapture: ImageCapture? = remember { null }

    val outputDirectory = context.cacheDir
    val executor = ContextCompat.getMainExecutor(context)

    AndroidView(factory = {
        previewView
    }, modifier = Modifier.fillMaxSize()) { view ->
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(view.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .setTargetRotation(view.display.rotation)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                onError("Camera initialization failed: ${e.message}")
            }
        }, executor)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Button(
            onClick = {
                val photoFile = File(outputDirectory, "${System.currentTimeMillis()}.jpg")
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                imageCapture?.takePicture(
                    outputOptions,
                    executor,
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val savedUri = Uri.fromFile(photoFile)
                            onImageCaptured(savedUri)

                            // Navigate to RatingView with URI path
                            navController.navigate("reportView?imageUri=${savedUri}")
                        }

                        override fun onError(exception: ImageCaptureException) {
                            onError("Photo capture failed: ${exception.message}")
                        }
                    }
                )
            },
            modifier = Modifier
                .padding(32.dp)
                .clip(CircleShape)
        ) {
            Text("Capture")
        }

        // Home button
        RectButton(text = "", icon = Icons.Default.Home,
            onClick = { navController.navigate("homeView") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            backgroundColor = Color(0xFF4CAF50)
        )
    }
}
