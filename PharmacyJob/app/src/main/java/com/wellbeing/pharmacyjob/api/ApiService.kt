package com.wellbeing.pharmacyjob.api

import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.JobDetail
import com.wellbeing.pharmacyjob.model.JobListResponse
import com.wellbeing.pharmacyjob.model.LoginRequest
import com.wellbeing.pharmacyjob.model.LoginResponse
import com.wellbeing.pharmacyjob.model.NegotiateAddRequest
import com.wellbeing.pharmacyjob.model.NegotiateUpdateRequest
import com.wellbeing.pharmacyjob.model.NegotiationResponse
import com.wellbeing.pharmacyjob.model.RegUserRequest
import com.wellbeing.pharmacyjob.model.UpdateJobRequest
import com.wellbeing.pharmacyjob.model.UserDetail
import com.wellbeing.pharmacyjob.model.UserDocListResponse
import com.wellbeing.pharmacyjob.model.UserUpdateRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //@POST("/api/auth/v1/register")
    @POST("api/mock/")
    suspend fun registerNewUser(@Body regUserRequest: RegUserRequest): Response<ApiResponse<String>>

    //@POST("/api/auth/v1/request-password-reset")
    @GET("api/mock/8")
    suspend fun requestPwReset(
//        @Body email: String
        @Query("email") email: String
    ): Response<ApiResponse<String>>

    //@POST("/api/auth/v1/login")
    @POST("api/mock/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ApiResponse<LoginResponse>>

    // @GET("/api/v1/job")
    @GET("/api/mock/2")
    suspend fun getAvailableJob(
        @Query("fromDate") fromDate: String,
        @Query("toDate") toDate: String,
        @Query("orderBy") orderBy: String
    ): Response<JobListResponse>

    // @GET("/api/v1/job")
    @GET("api/mock/2")
    suspend fun getMyFavoriteJob(@Query("jobIDs") favoriteIds: String): Response<JobListResponse>

    // @GET("/api/v1/myjob")
    @GET("api/mock/2")
    suspend fun getMyJob(@Query("status") status: String): Response<JobListResponse>

    //    @GET("/api/v1/job/{id}")
    @GET("api/mock/7")
    suspend fun getJobDetail(
        @Query("id") id: String
//        @Path("id") id: String
    ): Response<ApiResponse<JobDetail>>

    //     @PUT("/api/v1/job/{id}")
    @POST("api/mock/")
    suspend fun updateJobStatus(
//        @Path("id") id: String
        @Query("id") id: String,
        @Body updateJobRequest: UpdateJobRequest
    ): Response<ApiResponse<String>>

    //    @POST("/api/v1/negotiation")
    @POST("api/mock/")
    suspend fun addNegotiation(
        @Body negotiateAddRequest: NegotiateAddRequest
    ): Response<ApiResponse<String>>

    //    @GET("/api/v1/negotiation")
    @GET("api/mock/3")
    suspend fun getNegotiation(): Response<NegotiationResponse>

    @PUT("/api/v1/negotiation/{id}")
    suspend fun updateNegotiation(
        @Path("id") id: String,
        @Body negotiateUpdateRequest: NegotiateUpdateRequest
    ): Response<ApiResponse<String>>

    //@GET("/api/v1/myprofile/")
    @GET("api/mock/12")
    suspend fun getUserDetail(): Response<ApiResponse<UserDetail>>

    //@PUT("/api/v1/myprofile/")
    @POST("api/mock/")
    suspend fun updateUserDetail(
        @Body userUpdateRequest: UserUpdateRequest
    ): Response<ApiResponse<String>>

    //    @POST("/api/v1/mydoc/upload")
    @GET("api/mock/8")
    suspend fun uploadDoc(
        @Query("imageType") imageType: String,
        @Part imageFile: MultipartBody.Part
    ): Response<ApiResponse<String>>


    //    @GET("/api/v1/mydoc")
    @GET("api/mock/13")
    suspend fun getMyDocList(): Response<UserDocListResponse>

    @GET("/api/v1/mydoc/download/{id}")
    suspend fun downloadDoc(
        @Path("id") id: String
    ): Response<MultipartBody.Part>


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
