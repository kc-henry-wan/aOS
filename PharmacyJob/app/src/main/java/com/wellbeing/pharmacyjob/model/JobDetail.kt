package com.wellbeing.pharmacyjob.model

data class JobDetail(
    val jobId: String,
    val jobRef: String,
    val jobDate: String,
    val jobStartTime: String,
    val jobEndTime: String,
    val hourlyRate: Int,
    val totalPaid: Int,
    val totalPaidHour: Int,
    val lunchArrangement: String,
    val parkingOption: String,
    val ratePerMile: Double,
    val status: String,
    val updatedAt: String,
    val branchName: String,
    val branchAddress1: String,
    val branchAddress2: String,
    val branchPostalCode: String
)

