package com.example.aislepoc.repository

import com.example.aislepoc.api.ApiService
import com.example.aislepoc.data.LoginRequest
import com.example.aislepoc.data.LoginResponse
import com.example.aislepoc.data.auto.ProfileResponse
import com.example.aislepoc.utils.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProfileRepository @Inject constructor(val apiService: ApiService) {

    private val _profileResponse : MutableStateFlow<NetworkResult<ProfileResponse>> = MutableStateFlow(NetworkResult.Loading());
    val profileResponse : StateFlow<NetworkResult<ProfileResponse>>
        get() {
            return _profileResponse
        }

    suspend fun getProfile(token: String){
        val response = apiService.getProfiles(token = token)
        if(response.isSuccessful && response.body()!=null){
            _profileResponse.emit(NetworkResult.Success(response.body()!!))
        } else if(response.errorBody()!=null) {
            val errorObj = Gson().fromJson(response.errorBody()!!.charStream(), ProfileResponse::class.java)
            errorObj?.let {
                if(it.invites?.profiles?.isEmpty() == true){
                    _profileResponse.emit(NetworkResult.Error("Please try again later"))
                }
            }
        } else {
            _profileResponse.emit(NetworkResult.Error("Something Went Wrong"))
        }
    }


}
