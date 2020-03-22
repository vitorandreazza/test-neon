package com.example.neontest.data.source

import com.example.neontest.data.Result
import com.example.neontest.data.model.GenerateTokenResponse
import com.example.neontest.data.source.local.DefaultSharedPreferences
import com.example.neontest.data.source.remote.TokenService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class TokenRepositoryTest {

    private val tokenService: TokenService = mock()
    private val sharedPreferences: DefaultSharedPreferences = mock()
    private val tokenRepository = TokenRepository(tokenService, sharedPreferences)

    @Test
    fun `should request token and save on shared preferences`() {
        val name = "vitor"
        val email = "vitor@vitor.com"
        val token = "token"
        runBlocking {
            whenever(tokenService.generateToken(name, email))
                .thenReturn(Response.success(GenerateTokenResponse(token)))

            val result = tokenRepository.generateToken(name, email)

            verify(tokenService).generateToken(name, email)
            verify(sharedPreferences).saveToken(token)
            assert(result is Result.Success)
            assertEquals(token, (result as Result.Success).data.token)
        }
    }
}