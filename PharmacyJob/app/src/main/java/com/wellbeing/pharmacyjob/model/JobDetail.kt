package com.wellbeing.pharmacyjob.model

data class JobDetail(
    val jobID: String,
    val branchName: String,
    val jobDate: String,
    val jobStartTime: String,
    val jobEndTime: String,
    val hourlyRate: Int,
    val totalPaid: Int,
    val totalPaidHour: Int,
    val lunchArrangement: String,
    val parkingOption: String,
    val ratePerMile: Double,
    val address: String,
    val postalCode: String,
    val status: String,
    val statusCode: String
)

