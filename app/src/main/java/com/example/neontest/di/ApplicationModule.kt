package com.example.neontest.di

import android.content.Context
import android.content.SharedPreferences
import com.example.neontest.data.source.local.DefaultSharedPreferences
import com.example.neontest.data.source.remote.DigitalAccountService
import com.example.neontest.data.source.remote.ServiceGenerator
import com.example.neontest.data.source.remote.TokenService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideTokenService(serviceGenerator: ServiceGenerator): TokenService =
        serviceGenerator.createService(TokenService::class.java)

    @Singleton
    @Provides
    fun provideDigitalAccountService(
        serviceGenerator: ServiceGenerator,
        sharedPreferences: DefaultSharedPreferences
    ) =
        serviceGenerator.createService(
            DigitalAccountService::class.java,
            sharedPreferences.getToken()
        )

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
}
