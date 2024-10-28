package com.wellbeing.pharmacyjob.api

import com.wellbeing.pharmacyjob.model.JobListResponse
import com.wellbeing.pharmacyjob.model.LoginRequest
import com.wellbeing.pharmacyjob.model.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/mock/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/api/mock/2")
    suspend fun getAvailableJob(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("sortBy") sortBy: String
    ): Response<JobListResponse>

    @GET("api/mock/2")
    suspend fun getMyFavoriteJob(@Query("favoriteIds") favoriteIds: String): Response<JobListResponse>

    @GET("api/mock/2")
    suspend fun getMyJob(@Query("userId") userId: String): Response<JobListResponse>

//
////    @GET("api/mock/34")
////    fun getNegotiationJobs(): Call<JobsResponse>
//
////    @GET("api/mock/33")
////    fun getConfirmedJobs(): Call<JobsResponse>
//
////    @POST("api/mock/47/{jobID}")
////    fun acceptNegotiation(
////        @Path("jobID") jobID: String,
////        @Body request: AcceptRequest
////    ): Call<AcceptResponse>
//
//    @GET("api/mock/23/{userId}")
//    fun getUserDetails(@Path("userId") userId: String): Call<UserDetails>
//
//    @POST("api/mock/25/")
//    fun updateUserDetails(@Body userDetails: UserDetails): Call<Void>
//
//    @POST("api/mock/25/")
//    fun createUserAccount(@Body userDetails: UserDetails): Call<Void>
//
//    @GET("api/mock/4")
//    //@GET("api/mock/4/{jobId}")
//
//    fun getJobDetails(@Path("jobId") jobId: String): Call<JobDetail>
//
//    @POST("api/mock/7/{jobId}")
//    @FormUrlEncoded
//    fun applyForJob(
//        @Path("jobId") jobId: String,
//        @Field("pharmacistId") pharmacistId: String,
//        @Field("action") action: String = "apply"
//    ): Call<Void>
//
//    @POST("api/mock/6/{jobId}")
//    @FormUrlEncoded
//    fun withdrawFromJob(
//        @Path("jobId") jobId: String,
//        @Field("pharmacistId") pharmacistId: String,
//        @Field("action") action: String = "withdraw"
//    ): Call<Void>
//
//    @POST("api/mock/5")
//    @FormUrlEncoded
//    fun negotiateJob(
//        @Field("jobId") jobId: String,
//        @Field("pharmacistId") pharmacistId: String,
//        @Field("proposedHourRate") proposedHourRate: Double,
//        @Field("proposedTotal") proposedTotal: Double,
//        @Field("reason") reason: String
//    ): Call<Void>
//
//
//    @POST("mock/9/")
//    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
