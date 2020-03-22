package com.example.neontest.data.source

import com.example.neontest.data.Result
import com.example.neontest.data.model.SendMoneyRequest
import com.example.neontest.data.source.remote.DigitalAccountService
import com.example.neontest.ui.transfer.ContactItem
import com.example.neontest.utils.safeApiCall
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DigitalAccountRepository @Inject constructor(
    private val digitalAccountService: DigitalAccountService
) {

    suspend fun getContacts() = safeApiCall(
        call = {
            Thread.sleep(1000)
            digitalAccountService.getContacts()
        },
        errorMessage = "Error getting contacts"
    )

    suspend fun sendMoney(contact: ContactItem, value: Float): Result<Boolean> {
        val result = safeApiCall(
            call = {
                Thread.sleep(1000)
                digitalAccountService.sendMoney(SendMoneyRequest(contact.id, value))
            },
            errorMessage = "Error sending money"
        )
        return if (result is Result.Success && result.data.success) {
            Result.Success(true)
        } else {
            Result.Error(IOException("Send money response was success false"))
        }
    }

    suspend fun getTransfers() = safeApiCall(
        call = {
            Thread.sleep(1000)
            digitalAccountService.getTransfers()
        },
        errorMessage = "Error getting transfers"
    )
}