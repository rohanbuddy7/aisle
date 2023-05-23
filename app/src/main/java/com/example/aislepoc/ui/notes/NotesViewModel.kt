package com.example.aislepoc.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aislepoc.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(val repository: ProfileRepository): ViewModel() {

    val profileResponse = repository.profileResponse

    fun getProfile(token: String){
        viewModelScope.launch{
            repository.getProfile(token)
        }
    }

}