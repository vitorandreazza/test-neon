package com.example.neontest.data.source.remote

import com.example.neontest.data.model.GenerateTokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TokenService {

    @GET("/GenerateToken")
    suspend fun generateToken(
        @Query("name") name: String,
        @Query("email") email: String
    ): Response<GenerateTokenResponse>
}