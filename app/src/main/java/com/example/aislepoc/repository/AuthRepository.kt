package com.example.aislepoc.repository

import com.example.aislepoc.api.ApiService
import com.example.aislepoc.data.LoginRequest
import com.example.aislepoc.data.OtpRequest
import com.example.aislepoc.data.LoginResponse
import com.example.aislepoc.data.OtpResponse
import com.example.aislepoc.utils.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class AuthRepository @Inject constructor(val apiService: ApiService) {

    private val _loginResponse : MutableStateFlow<NetworkResult<LoginResponse>> = MutableStateFlow(NetworkResult.Loading());
    val loginResponse : StateFlow<NetworkResult<LoginResponse>>
        get() {
            return _loginResponse
        }

    private val _verifyotpResponse : MutableStateFlow<NetworkResult<OtpResponse>> = MutableStateFlow(NetworkResult.Loading());
    val verifyotpResponse : StateFlow<NetworkResult<OtpResponse>>
        get() {
            return _verifyotpResponse
        }


    suspend fun getOtp(phone: String){
        val response = apiService.getOtp(loginRequest = LoginRequest(phone))
        if(response.isSuccessful && response.body()!=null){
            _loginResponse.emit(NetworkResult.Success(response.body()!!))
        } else if(response.errorBody()!=null) {
            val errorObj = Gson().fromJson(response.errorBody()!!.charStream(), LoginResponse::class.java)
            errorObj?.let {
                if(!it.status){
                    _loginResponse.emit(NetworkResult.Error("Please try again later"))
                }
            }
        } else {
            _loginResponse.emit(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun verifyOtp(phone: String, otp: String){
        val response = apiService.verifyOtp(OtpRequest(number = phone, otp = otp))
        if(response.isSuccessful && response.body()!=null){
            _verifyotpResponse.emit(NetworkResult.Success(response.body()!!))
        } else if(response.errorBody()!=null) {
                val errorObj = Gson().fromJson(response.errorBody()!!.charStream(), OtpResponse::class.java)
            errorObj?.let {
                if(it.token==null){
                    _verifyotpResponse.emit(NetworkResult.Error("Please try again later"))
                }
            }
        } else {
            _verifyotpResponse.emit(NetworkResult.Error("Something Went Wrong"))
        }
    }




}
