package com.example.chessgo.backend.global

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import com.example.chessgo.R
import com.example.chessgo.backend.registration.Results
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

/**
 * PhotoPickerManager is a utility class for managing image-related operations, such as getting image URIs,
 * uploading images to Firebase Storage, and retrieving the URI of the last selected image.
 */
class PhotoPickerManager : FileProvider(
    R.xml.filepaths
) {
    companion object{
        private val imageUris: MutableState<Uri?> = mutableStateOf(null)
        private val storage = Firebase.storage
        private val storageRef = storage.reference
        private var _currentEvent: String = ""
        var currentEvent: String
            get() {
                return _currentEvent
            }
            set(value) {
                _currentEvent = value
            }
        /**
         * Gets a temporary image URI for capturing an image using the device's camera.
         *
         * @param context The application context.
         * @return The temporary image URI.
         */
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
            imageUris.value = uri
            return uri

        }
        /**
         * Uploads the specified image URI to Firebase Storage.
         *
         * @param uri The URI of the image to be uploaded.
         * @param context The application context.
         * @param callback Callback function to handle the result of the upload operation.
         */
        fun uploadImageToStorage(uri: Uri?, context: Context, callback: (Results<Uri>) -> Unit) {
            //TODO change to event uid wh
            val uid = ClientManager.getClient().uid
            val gameIRL = ClientManager.userGameIRL

            var pictureName = "gameresult"

            pictureName += if (gameIRL.host == uid) "host" else "enemy"

            val pathToImage = "results/${gameIRL.gid}/$pictureName.jpg"

            val imagePathRef = storageRef.
                child(pathToImage)

            val byteArray: ByteArray? = uri?.let {
                context.contentResolver
                    .openInputStream(it)
                    ?.use { it.readBytes() }
            }

            byteArray?.let{
                val uploadTask = imagePathRef.putBytes(byteArray)
                uploadTask
                    .addOnSuccessListener { _->
                        // Image uploaded successfully
                        // Get the download URL and include it in the success result
                        ClientManager.currentPicture = pathToImage
                        imagePathRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            callback(Results.Success(downloadUri))
                        }
                    }
                    .addOnFailureListener { exception ->
                        callback(Results.Failure(exception))
                }
            }
        }
        /**
         * Gets the URI of the last selected image.
         *
         * @return The URI of the last selected image.
         */
        fun getLastImageUri(): Uri? {
            return imageUris.value
        }
    }
}