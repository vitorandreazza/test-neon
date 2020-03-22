package com.example.neontest.data.source.remote

import com.example.neontest.data.mock.MockInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceGenerator @Inject constructor() {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(MockInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))

    fun <T> createService(serviceClass: Class<T>): T = createService(serviceClass, null)

    fun <T> createService(serviceClass: Class<T>, authToken: String?): T {
        if (!authToken.isNullOrBlank()) {
            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", authToken)
                    .build()
                chain.proceed(request)
            }
        }
        return builder.client(httpClient.build()).build().create(serviceClass)
    }

    companion object {

//        private const val BASE_URL = "http://processoseletivoneon.azurewebsites.net/"
        private const val BASE_URL = "https://www.youtube.com/"
    }
}