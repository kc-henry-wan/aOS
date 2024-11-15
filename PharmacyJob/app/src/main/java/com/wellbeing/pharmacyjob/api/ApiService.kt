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

    // @POST("/api/auth/v1/register")
    // suspend fun registerNewUser(@Body regUserRequest: RegUserRequest): Response<CommonApiResponse>

    // @POST("/api/auth/v1/request-password-reset")
    // suspend fun requestPwReset(@uery("email") email: String): Response<CommonApiResponse>
    
    //@POST("/api/auth/v1/login")
    @POST("api/mock/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // @GET("/api/v1/job")
    @GET("/api/mock/2")
    suspend fun getAvailableJob(
        @Query("fromDate") fromDate: String,
        @Query("toDate") toDate: String,
        @Query("orderBy") orderBy: String,
        @Query("fromLat") fromLat: String,
        @Query("fromLng") fromLng: String
    ): Response<JobListResponse>

    // @POST("/api/v1/job")
    @GET("api/mock/2")
    suspend fun getMyFavoriteJob(@Query("jobIDs") favoriteIds: String): Response<JobListResponse>

    // @POST("/api/v1/myjob")
    @GET("api/mock/2")
    suspend fun getMyJob(
        @Query("fromLat") fromLat: String,
        @Query("fromLng") fromLng: String
    ): Response<JobListResponse>

//     @PUT("/api/v1/job/{id}")
//     suspend fun updateJobStatus(
//         @Path("id") id: Long
//         @Body jobUpdateRequest:JobUpdateRequest
//     ):Response<CommonApiResponse>
    
//     @POST("/api/v1/negotiation")
//     suspend fun addNegotiation(
//         @Body addNegotiationRequest:AddNegotiationRequest
//     ):Response<CommonApiResponse>

//     //@GET("/api/v1/negotiation")
// //    @GET("api/mock/34")
// //   suspend fun getNegotiationJobs():Responsel<NegotiationList>

//     @GET("/api/v1/negotiation/{id}")
//     suspend fun getNegotiationRecord(@Path("id") id: Long):Response<Negotiation>
    
//     @PUT("/api/v1/negotiation/{id}")
//     suspend fun updateNegotiationRecord(
//         @Path("id") id: Long
//         @Body updateNegotiationRequest:UpdateNegotiationRequest
//     ):Response<CommonApiResponse>
    
//     @GET("/api/v1/pharmacist/{id}")
//     suspend fun getUserDetail(@Path("id") id: Long):Response<Pharmacist>
    
//     @PUT("/api/v1/pharmacist/{id}")
//     suspend fun updateUser(
//         @Path("id") id: Long
//         @Body updateUserRequest:UpdateUserRequest
//     ):Response<CommonApiResponse>
    
//     @POST("/api/v1/image/upload")
//     suspend fun uploadImage(
//         @Query("imageType") imageType: String,
//         @Query("imageFile") imageFile: String
//     ):Response<CommonApiResponse>
    
//     @GET("/api/v1/image/pharmacist/{id}")
//     suspend fun getUserImageList(
//         @Path("id") id: Long
//     ):Response<imageListResponse>
    
//     @GET("/api/v1/image/download/{id}")
//     suspend fun downloadImage(
//         @Path("id") id: Long
//     ):Response<image>
    
    
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
