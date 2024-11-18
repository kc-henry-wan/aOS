package com.wellbeing.pharmacyjob.model

data class LoginRequest(
    val id: String = "1",
    val username: String,
    val password: String
//    val email: String,
//    val hashedPw: String
)
