package com.wellbeing.pharmacyjob.model

data class JobListResponse(
    val apiVersion: String,
    val apiStatus: String,
    val errorCode: String,
    val errorMessage: String,
    val data: ContentJobList
)

data class ContentJobList(
    val content: List<JobList>
)
