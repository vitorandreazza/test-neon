package com.example.neontest.data.model

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("avatarUrl") val avatarUrl: String
)