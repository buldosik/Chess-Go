package com.example.chessgo.frontend


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.chessgo.backend.global.PhotoPickerManager



@Composable
fun CameraScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val hasImage = remember {
        mutableStateOf(false)
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage.value = success
        })
    //checks permissions
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val uri = PhotoPickerManager.getImageUri(context)
                cameraLauncher.launch(uri)
            }
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                   cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                },
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontSize = 14.sp)
                        ) {
                            append("Take photo")
                        }
                    }
                )
            }
        }
    }
    //loads taken photo
    if (hasImage.value) {
        val lastImageUri = PhotoPickerManager.getLastImageUri()
        lastImageUri?.let {
            AsyncImage(
                model = it,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Selected image",
            )
        }
    }
}