package com.example.aislepoc.data

import com.google.gson.annotations.SerializedName

data class OtpResponse(

	@field:SerializedName("token")
	val token: String? = null
)
