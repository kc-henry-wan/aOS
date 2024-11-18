package com.wellbeing.pharmacyjob.model

data class NegotiateAddRequest(
    val mode: String,
    val counterHourlyRate: String,
    val counterTotalPaid: String,
    val updatedAt: String
)
