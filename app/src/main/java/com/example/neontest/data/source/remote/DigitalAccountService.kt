package com.example.neontest.data.source.remote

import com.example.neontest.data.model.Contact
import com.example.neontest.data.model.SendMoneyRequest
import com.example.neontest.data.model.SendMoneyResponse
import com.example.neontest.data.model.Transfer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DigitalAccountService {

    @GET("/GetContacts")
    suspend fun getContacts(): Response<List<Contact>>

    @POST("/SendMoney")
    suspend fun sendMoney(@Body sendMoneyRequest: SendMoneyRequest): Response<SendMoneyResponse>

    @POST("/GetTransfers")
    suspend fun getTransfers(): Response<List<Transfer>>
}