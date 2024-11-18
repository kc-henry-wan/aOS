package com.wellbeing.pharmacyjob.model

data class NegotiateJobResponse(
    val apiVersion: String,
    val apiStatus: String,
    val errorCode: String,
    val errorMessage: String,
    val data: ContentNegotitationList
)

data class ContentNegotitationList(
    val content: List<NegotiationList>
)
