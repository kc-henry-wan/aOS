package com.wellbeing.pharmacyjob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NegotiationList(
    val negotiationId: String,
    val jobId: String,
    val jobRef: String,
    val jobDate: String,
    val jobStartTime: String,
    val jobEndTime: String,
    val totalWorkHour: Double,
    val lunchArrangement: String,
    val parkingOption: String,
    val ratePerMile: Double,
    val originalHourlyRate: Double,
    val originalTotalPaid: Double,
    val purposedHourlyRate: Double,
    val purposedTotalPaid: Double,
    val counterHourlyRate: Double,
    val counterTotalPaid: Double,
    val status: String,
    val reason: String,
    val updatedUserId: String,
    val updatedAt: String,
    val branchName: String,
    val branchAddress1: String,
    val branchAddress2: String,
    val branchPostalCode: String,
    val pharmacistFirstName: String,
    val pharmacistLastName: String
) : Parcelable
