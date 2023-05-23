package com.example.aislepoc.api


import com.example.aislepoc.data.LoginRequest
import com.example.aislepoc.data.OtpRequest
import com.example.aislepoc.data.LoginResponse
import com.example.aislepoc.data.OtpResponse
import com.example.aislepoc.data.auto.ProfileResponse
import com.example.aislepoc.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("users/phone_number_login")
    suspend fun getOtp(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("users/verify_otp")
    suspend fun verifyOtp(
        @Body otpRequest: OtpRequest
    ): Response<OtpResponse>

    @GET("users/test_profile_list")
    suspend fun getProfiles(
        @Header(Constants.Authorization) token: String,
    ): Response<ProfileResponse>


}