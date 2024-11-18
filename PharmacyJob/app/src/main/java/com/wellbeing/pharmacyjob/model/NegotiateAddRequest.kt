package com.wellbeing.pharmacyjob.model

data class NegotiateAddRequest(
    val id: String = "8",
    val mode: String,
    val purposedHourlyRate: String,
    val purposedTotalPaid: String
)
