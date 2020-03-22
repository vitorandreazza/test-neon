package com.example.neontest.data.model

import com.google.gson.annotations.SerializedName

data class SendMoneyRequest(
    @SerializedName("clientId") val contactId: Long,
    @SerializedName("value") val value: Float
)