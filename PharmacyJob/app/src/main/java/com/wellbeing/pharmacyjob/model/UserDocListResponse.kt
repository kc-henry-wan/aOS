package com.wellbeing.pharmacyjob.model

data class UserDocListResponse(
    val apiVersion: String,
    val apiStatus: String,
    val errorCode: String,
    val errorMessage: String,
    val data: ContentUserDocList
)

data class ContentUserDocList(
    val content: List<UserDoc>
)
