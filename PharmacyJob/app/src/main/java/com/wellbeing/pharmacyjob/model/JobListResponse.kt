package com.wellbeing.pharmacyjob.model

data class JobListResponse(
//    @SerializedName("status") val status: String,
//    @SerializedName("responseCode") val responseCode: String,
//    @SerializedName("errorCode") val errorCode: String,
//    @SerializedName("jobs") val jobs: List<JobList>
    val status: String,
    val responseCode: String,
    val errorCode: String,
    val jobs: List<JobList>
)
