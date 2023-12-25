package com.example.chessgo.backend.global

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.chessgo.R
import java.io.File

class PhotoPickerManager : FileProvider(
    R.xml.filepaths
) {
    companion object{
        private val imageUris = mutableListOf<Uri>()
        fun getImageUri(context: Context): Uri{
            // get path where file will be stored
            //it has to match one of paths in filepath.xml
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            //creates temp folder
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            //gets authority for content provider
            val authority = context.packageName + ".fileprovider"
            val uri = getUriForFile(
                context,
                authority,
                file,
            )
            imageUris.add(uri)
            return uri
        }
        fun getLastImageUri(): Uri? {
            return imageUris.lastOrNull()
        }
    }

}