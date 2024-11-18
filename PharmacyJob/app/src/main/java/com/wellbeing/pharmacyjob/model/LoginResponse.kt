package com.wellbeing.pharmacyjob.model

data class LoginResponse(
    val sessionKey: String,  // The session key returned from the API
    val userId: String,
    val userName: String,
    val userLat: String,
    val userLng: String

)
