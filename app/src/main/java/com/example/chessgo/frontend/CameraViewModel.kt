package com.example.chessgo.frontend

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chessgo.backend.global.PhotoPickerManager
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.frontend.navigation.navigateToMyEventsMenu

private const val TAG = "SignUpViewModel"
/**
 * ViewModel for managing camera-related functionality in the CameraScreen.
 *
 * @param navController The NavController for handling navigation.
 * @param context The application context.
 */
class CameraViewModel(
    val navController: NavHostController,
    val context: Context
) : ViewModel(){

    /**
     * Handles the click event when the user confirms a photo.
     *
     * @param uri The Uri of the selected photo.
     */
    fun onConfirmPhotoClick(uri: Uri?) {

        if (uri != null) {
            PhotoPickerManager.uploadImageToStorage(uri, context) { result ->
                when (result) {
                    is Results.Success -> {
                        navController.navigateToMyEventsMenu()
                    }
                    is Results.Failure -> {
                        val exception = result.exception
                        Log.w(TAG, "safeUserToStore:failure", exception)
                        makeToast("Failed to safe image")
                    }
                }
            }
        }else{
            makeToast("Take new photo first")
        }
    }
    /**
     * Gets the Uri of the last selected image.
     *
     * @return The Uri of the last selected image, or null if none.
     */
    fun getLastImageUri(): Uri? {
        return PhotoPickerManager.getLastImageUri()
    }
    fun getImageUri(context: Context): Uri? {
        return PhotoPickerManager.getImageUri(context)
    }
    // Function to convert Uri to Bitmap
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = uri.let { context.contentResolver.openInputStream(it) }
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            null
        }
    }

    // Function to check if Bitmap is empty
    fun isBitmapEmpty(bitmap: Bitmap): Boolean {
        return bitmap.width == 0 || bitmap.height == 0
    }
    //can be used somewhere globally
    fun makeToast(text: String){
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT,
        ).show()
    }
}