package com.example.chessgo.backend.global

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import com.example.chessgo.R
import com.example.chessgo.backend.registration.Results
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.UUID

class PhotoPickerManager : FileProvider(
    R.xml.filepaths
) {
    companion object{
        private val imageUris: MutableState<Uri?> = mutableStateOf(null)
        private val storage = Firebase.storage
        private val storageRef = storage.reference
        fun getImageUri(context: Context): Uri? {
            // Get the path where the file will be stored
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()

            // Create a temporary file in the specified directory
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )

            // Get the authority for the content provider
            val authority = context.packageName + ".fileprovider"

            // Get a Uri for the file using FileProvider
            val uri = getUriForFile(
                context,
                authority,
                file
            )
            imageUris.value = (uri)
            return uri

        }
        fun getLastImageUri(): Uri? {
            return imageUris.value
        }
        fun uploadImageToStorage(uri: Uri?, context: Context, callback: (Results<Uri>) -> Unit) {
            //should be changed based on event uid
            val uniqueImageName = UUID.randomUUID()

            val pictureName = "gameresult"

            val imagePathRef = storageRef.
                child("results/$uniqueImageName/$pictureName.jpg")

            val byteArray: ByteArray? = uri?.let {
                context.contentResolver
                    .openInputStream(it)
                    ?.use { it.readBytes() }
            }

            byteArray?.let{
                val uploadTask = imagePathRef.putBytes(byteArray)
                uploadTask
                    .addOnSuccessListener { taskSnapshot ->
                        // Image uploaded successfully
                        // Get the download URL and include it in the success result
                        imagePathRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            callback(Results.Success(downloadUri))
                        }
                    }
                    .addOnFailureListener { exception ->
                        callback(Results.Failure(exception))
                }
            }
        }
    }
}