package com.rviannaoliveira.vroutetrack.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*

/**
 * Criado por rodrigo on 18/05/17.
 */
object RouteUtil {

    private val COMMA = ", "

    private fun getAddresses(context: Context?, latLng: LatLng?): List<Address>? {
        try {
            if (latLng == null || context == null) {
                return null
            }
            val geo = Geocoder(context.applicationContext, Locale.getDefault())
            return geo.getFromLocation(latLng.latitude, latLng.longitude, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun getAddressLabel(context: Context?, latLng: LatLng?): String {
        val addresses = getAddresses(context, latLng)

        try {
            if (addresses == null || addresses.isEmpty()) {
                return ""
            }
            var addressComplete = formatAddressAutoComplete(addresses[0].thoroughfare, addresses[0].subThoroughfare) ?: return ""

            val neighborhood = addresses[0].subLocality

            if (neighborhood != null) {
                addressComplete = addressComplete + " - " + neighborhood
            }

            return addressComplete

        } catch (e: Exception) {
            return ""
        }

    }

    private fun formatAddressAutoComplete(primaryText: String, secondaryText: String?): String {
        if (secondaryText == null) {
            return primaryText
        }
        if (!secondaryText.contains(COMMA)) {
            return primaryText + COMMA + secondaryText
        }
        return primaryText + COMMA + secondaryText.substring(0, secondaryText.indexOf(","))
    }
}