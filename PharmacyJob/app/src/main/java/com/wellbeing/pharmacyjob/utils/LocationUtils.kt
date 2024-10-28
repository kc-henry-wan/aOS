package com.wellbeing.pharmacyjob.utils

import kotlin.math.*
import android.location.Geocoder
import android.widget.Toast
import java.io.IOException
import java.util.*

object LocationUtils {
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371e3 // meters
        val phi1 = lat1 * Math.PI / 180 // φ in radians
        val phi2 = lat2 * Math.PI / 180 // φ in radians
        val deltaPhi = (lat2 - lat1) * Math.PI / 180 // Δφ in radians
        val deltaLambda = (lon2 - lon1) * Math.PI / 180 // Δλ in radians

        val a = sin(deltaPhi / 2) * sin(deltaPhi / 2) +
                cos(phi1) * cos(phi2) *
                sin(deltaLambda / 2) * sin(deltaLambda / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c / 1609.34 // Convert to miles
    }

//    private fun getCoordinates(address: String) {
//        val geocoder = Geocoder(this, Locale.getDefault())
//        try {
//            // Get the list of addresses for the given input address
//            val addressList = geocoder.getFromLocationName(address, 1)
//            if (addressList != null && addressList.isNotEmpty()) {
//                val location = addressList[0]
//                val latitude = location.latitude
//                val longitude = location.longitude
//
//                // Display the latitude and longitude
//                Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//            Toast.makeText(this, "Geocoder service not available", Toast.LENGTH_SHORT).show()
//        }
//    }
}
