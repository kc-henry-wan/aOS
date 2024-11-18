package com.wellbeing.pharmacyjob.model

data class ApiResponse<T>(
    val apiStatus: String,
    val apiVersion: String,
    val errorCode: String?,
    val errorMessage: String?,
    val data: T?
)
