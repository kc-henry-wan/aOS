package com.wellbeing.pharmacyjob.model

data class RegUserRequest(
    val id: String = "8",
    val email: String,
    val firstName: String,
    val lastName: String,
    val mobile: String,
    val address1: String,
    val address2: String,
    val postalCode: String
)
