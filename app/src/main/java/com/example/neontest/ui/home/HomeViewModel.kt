package com.example.neontest.ui.home

import androidx.lifecycle.viewModelScope
import com.example.neontest.data.source.TokenRepository
import com.example.neontest.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

    fun generateToken() {
        viewModelScope.launchWithLoading(Dispatchers.IO) {
            tokenRepository.generateToken("vitor", "vitor@vitor.com")
        }
    }
}