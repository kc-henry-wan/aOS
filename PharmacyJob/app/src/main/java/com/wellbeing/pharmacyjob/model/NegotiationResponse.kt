package com.wellbeing.pharmacyjob.model

data class NegotiationResponse(
    val apiVersion: String,
    val apiStatus: String,
    val errorCode: String,
    val errorMessage: String,
    val data: ContentNegotitationList
)

data class ContentNegotitationList(
    val content: List<NegotiationList>
)
