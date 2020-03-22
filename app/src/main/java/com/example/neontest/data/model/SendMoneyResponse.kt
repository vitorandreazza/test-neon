package com.example.neontest.data.model

import com.google.gson.annotations.SerializedName

data class SendMoneyResponse(
    @SerializedName("success") val success: Boolean
)