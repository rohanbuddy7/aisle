package com.example.aislepoc.ui.phoneNumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aislepoc.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor(val repository: AuthRepository): ViewModel()  {

    val phoneResponse = repository.loginResponse

    fun getOtp(phone: String){
        viewModelScope.launch{
            repository.getOtp(phone)
        }
    }

}