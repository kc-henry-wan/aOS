package com.wellbeing.pharmacyjob.model

data class JobListResponse(
//    @SerializedName("status") val status: String,
//    @SerializedName("responseCode") val responseCode: String,
//    @SerializedName("errorCode") val errorCode: String,
//    @SerializedName("jobs") val jobs: List<JobList>
    val apiVersion: String,
    val apiStatus: String,
    val errorCode: String,
    val errorMessage: String,
    val data: data
)

data class data(
    val content: List<JobList>
)
