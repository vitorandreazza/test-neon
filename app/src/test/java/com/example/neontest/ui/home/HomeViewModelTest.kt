package com.example.neontest.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.neontest.CoroutinesTestRule
import com.example.neontest.data.source.TokenRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule val coroutinesTestRule = CoroutinesTestRule()
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    private val tokenRepository: TokenRepository = mock()
    private val homeViewModel = HomeViewModel(tokenRepository)

    @Test
    fun `should request user token`() = runBlockingTest {
        homeViewModel.generateToken()

        verify(tokenRepository).generateToken("vitor", "vitor@vitor.com")
    }
}