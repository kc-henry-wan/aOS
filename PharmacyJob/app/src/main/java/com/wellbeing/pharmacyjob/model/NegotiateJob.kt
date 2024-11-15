package com.wellbeing.pharmacyjob.model


data class NegotiateJob(
    val negotiateId: Int? = null,
    val jobID: String,
    val jobDate: String,
    val jobStartTime: String,
    val jobEndTime: String,
    val totalWorkHour: Double,
    val originalHourlyRate: Double,
    val originalTotalPaid: Double,
    val purposedHourlyRate: Double,
    val purposedTotalPaid: Double,
    val counterHourlyRate: Double,
    val counterTotalPaid: Double,
    val totalPaidHour: Double,
    val lunchArrangement: String,
    val parkingOption: String,
    val ratePerMile: Double,
    val branchName: String,
    val branchAddress1: String,
    val branchAddress2: String,
    val branchPostalCode: String,
    val status: String,
    val updatedAt: String
)
