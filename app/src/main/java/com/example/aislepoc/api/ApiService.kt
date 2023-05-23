package com.example.aislepoc.api


import com.example.aislepoc.data.TokenResponse
import com.example.aislepoc.data.auto.ProfileResponse
import com.example.aislepoc.utils.Constants
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/users/phone_number_login")
    suspend fun getOtp(): Result<String>


    @POST("/users/verify_otp")
    suspend fun verifyOtp(): Result<TokenResponse>


    @POST("/users/test_profile_list")
    suspend fun getProfiles(
        @Header(Constants.Authorization) token: String,
    ): Result<ProfileResponse>


}