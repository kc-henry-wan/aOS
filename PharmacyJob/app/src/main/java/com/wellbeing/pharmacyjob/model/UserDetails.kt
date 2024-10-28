package com.wellbeing.pharmacyjob.model

data class UserDetails(
    val pharmacistId: String,
    val email: String,
    val mobile: String,
    val address: String,
    val postalCode: String,
    val licenseNumber: String,
    val info2: String,
    val info3: String,
    val info4: String,
    val info5: String,
    val status: String,
    val statusCode: String,
    val registerDateTime: String,
    val lastModifiedDateTime: String
)
