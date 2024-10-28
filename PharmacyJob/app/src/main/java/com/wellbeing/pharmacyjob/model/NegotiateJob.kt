package com.wellbeing.pharmacyjob.model


data class NegotiateJob(
    val negotiateJobId: Int? = null,
    val jobID: String,
    val branchName: String,
    val jobDate: String,
    val jobStartTime: String,
    val jobEndTime: String,
    val OriginalHourlyRate: Double,
    val OriginalTotalPaid: Double,
    val PurposedHourlyRate: Double,
    val PurposedTotalPaid: Double,
    val CounterHourlyRate: Double,
    val CounterTotalPaid: Double,
    val totalPaidHour: Double,
    val lunchArrangement: String,
    val parkingOption: String,
    val ratePerMile: Double,
    val address: String,
    val postalCode: String,
    val status: String,
    val statusCode: String
)