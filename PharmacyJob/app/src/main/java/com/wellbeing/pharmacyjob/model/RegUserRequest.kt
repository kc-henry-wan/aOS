package com.wellbeing.pharmacyjob.model

data class RegUserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val mobile: String,
    val address1: String,
    val address2: String,
    val postalCode: String
)
