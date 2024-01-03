package com.example.chessgo.backend.global

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.common.util.CollectionUtils
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class GeocoderUtils {
    fun getAddressFromPoint(
        context: Context,
        position: LatLng,
        callback: LoadDataCallback<String>
    ) {
        try {
            val addresses: MutableList<Address>?
            val geocoder = Geocoder(context, Locale.ENGLISH)
            addresses =
                geocoder.getFromLocation(position.latitude, position.longitude, 1)
            if (!CollectionUtils.isEmpty(addresses)) {
                val fetchedAddress = addresses!![0].getAddressLine(0)
                callback.onDataLoaded(fetchedAddress.toString())
            }
            else {
                callback.onDataNotAvailable(0, "There is no address")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onDataNotAvailable(1, "Something went wrong!")
        }
    }
}

interface LoadDataCallback<T> {
    fun onDataLoaded(response: T)
    fun onDataNotAvailable(errorCode: Int, reasonMsg: String)
}
