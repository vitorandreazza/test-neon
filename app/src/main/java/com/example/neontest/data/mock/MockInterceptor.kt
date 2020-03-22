package com.example.neontest.data.mock

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val responseString = when {
            uri.endsWith("GenerateToken") -> FAKE_TOKEN
            uri.endsWith("GetContacts") -> FAKE_CONTACTS
            uri.endsWith("SendMoney") -> FAKE_SEND_MONEY
            uri.endsWith("GetTransfers") -> FAKE_TRANSFERS
            else -> ""
        }
        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    responseString.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}