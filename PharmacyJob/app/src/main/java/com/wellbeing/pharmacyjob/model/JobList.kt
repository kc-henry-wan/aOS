package com.wellbeing.pharmacyjob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobList(
    val jobId: String,
    val jobRef: String,
    val jobDate: String,
    val jobStartTime: String,
    val jobEndTime: String,
    val hourlyRate: Double,
    val totalWorkHour: Double,
    val totalPaid: Double,
    val lunchArrangement: String,
    val parkingOption: String,
    val ratePerMile: Double,
    val status: String,
    val branchName: String,
    val branchAddress1: String,
    val branchAddress2: String,
    val branchPostalCode: String,
    var branchLatitude: Double = 0.0,
    var branchLongitude: Double = 0.0,
    var pharmacistFirstName: String,
    var pharmacistLastName: String,
    var distance: Double = 0.0

) : Parcelable
