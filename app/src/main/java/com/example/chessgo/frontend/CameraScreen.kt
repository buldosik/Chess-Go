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
import coil.compose.AsyncImage
import android.Manifest
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chessgo.R


@Composable
fun CameraScreen(
    navController: NavHostController
) {
    val image = R.drawable.pawn_icon

    val context = LocalContext.current
    val cameraScreenViewModel = CameraViewModel(navController, context)
    //used to launch new photos
    val hasImage = remember {
        mutableStateOf<Boolean>(false)
    }
    val currentUri = remember {
        mutableStateOf<Uri?>(Uri.EMPTY)
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()){
            currentUri.value = cameraScreenViewModel.getLastImageUri()
        }
    //checks permissions
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted ) {
                val uri = cameraScreenViewModel.getImageUri(context)
                cameraLauncher.launch(uri)
               // hasImage.value = false
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
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    val uri = cameraScreenViewModel.getLastImageUri()
                    // it is done to avoid case when user
                    // chooses to take a photo and then scrolls back and try to
                    // confirm photo even though photo wasn't confirmed in camera
                    val bitmap = uri?.let { cameraScreenViewModel.uriToBitmap(context, it) }
                    if (bitmap != null && !cameraScreenViewModel.isBitmapEmpty(bitmap)) {
                        cameraScreenViewModel.onConfirmPhotoClick(uri)

                    } else{
                        cameraScreenViewModel.makeToast("Sneaky bastard")
                    }
                },
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontSize = 14.sp)
                        ) {
                            append("Confirm photo")
                        }
                    }
                )
            }
        }
    }
    if (currentUri.value?.path?.isNotEmpty() == true) {
        val bitmap = currentUri.value?.let { cameraScreenViewModel.uriToBitmap(context, it) }
        if (bitmap != null && !cameraScreenViewModel.isBitmapEmpty(bitmap)) {

            System.out.println("1")
            Image(currentUri.value,"Selected image")

        } else{
            System.out.println("2")
            Image(image,"Default picture")
        }
    }else{
        System.out.println("3")
        Image(image,"Default picture")
    }


}

@Composable
fun Image(model: Any?, text: String?){
    AsyncImage(
        model = model,
        modifier = Modifier.fillMaxWidth(),
        contentDescription = text,
    )
}
