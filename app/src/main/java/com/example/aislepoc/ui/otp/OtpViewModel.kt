package com.example.aislepoc.ui.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aislepoc.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(val repository: AuthRepository): ViewModel() {

    val verifyotpResponse = repository.verifyotpResponse

    fun verifyOtp(phone: String, otp: String){
        viewModelScope.launch{
            repository.verifyOtp(phone, otp)
        }
    }

}