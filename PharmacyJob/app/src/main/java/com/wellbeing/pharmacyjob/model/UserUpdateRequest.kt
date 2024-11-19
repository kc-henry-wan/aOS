package com.wellbeing.pharmacyjob.model

data class UserUpdateRequest(
    val id: String = "8",
    val firstName: String,
    val lastName: String,
    val mobile: String,
    val address1: String,
    val address2: String,
    val postalCode: String,
//    val info1: String,
//    val info2: String,
//    val info3: String,
//    val info4: String,
//    val info5: String,
    val updatedAt: String
)
