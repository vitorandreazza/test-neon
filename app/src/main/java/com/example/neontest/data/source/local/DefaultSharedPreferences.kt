package com.example.neontest.data.source.local

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultSharedPreferences @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) {

    fun getToken() = sharedPreferencesManager.getString(KEY_TOKEN)

    fun saveToken(token: String) {
        sharedPreferencesManager.save(KEY_TOKEN to token)
    }

    companion object {

        private const val KEY_TOKEN = "token"
    }
}