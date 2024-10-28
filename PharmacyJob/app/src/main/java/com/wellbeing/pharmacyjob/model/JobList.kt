package com.wellbeing.pharmacyjob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobList(
    val jobID: String,
    val branchName: String,
    val jobDate: String,
    val jobStartTime: String,
    val jobEndTime: String,
    val hourlyRate: Double,
    val totalPaid: Double,
    val totalPaidHour: Double,
    val lunchArrangement: String,
    val parkingOption: String,
    val ratePerMile: Double,
    val address: String,
    val postalCode: String,
    val status: String,
    val statusCode: String,
    var distance: Double = 0.0, // Distance calculated based on location
    var latitude: Double = 0.0, // Add latitude
    var longitude: Double = 0.0 // Add longitude
) : Parcelable
