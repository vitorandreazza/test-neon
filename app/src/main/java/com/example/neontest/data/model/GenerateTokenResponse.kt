package com.example.neontest.data.model

import com.google.gson.annotations.SerializedName

data class GenerateTokenResponse(
    @SerializedName("token") val token: String
)