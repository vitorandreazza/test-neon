package com.example.neontest.data.source

import com.example.neontest.data.Result
import com.example.neontest.data.model.GenerateTokenResponse
import com.example.neontest.data.source.local.DefaultSharedPreferences
import com.example.neontest.data.source.remote.TokenService
import com.example.neontest.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    private val tokenService: TokenService,
    private val defaultSharedPreferences: DefaultSharedPreferences
) {

    suspend fun generateToken(name: String, email: String): Result<GenerateTokenResponse> {
        val result = safeApiCall(
            call = {
                Thread.sleep(1000)
                tokenService.generateToken(name, email)
            },
            errorMessage = "Error on token generation"
        )
        if (result is Result.Success) defaultSharedPreferences.saveToken(result.data.token)
        return result
    }
}
