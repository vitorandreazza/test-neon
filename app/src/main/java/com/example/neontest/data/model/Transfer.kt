package com.example.neontest.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Transfer(
    @SerializedName("id") val id: Long,
    @SerializedName("clientId") val clientId: Long,
    @SerializedName("value") val value: Float,
    @SerializedName("token") val token: String,
    @SerializedName("date") val date: Date
)