package com.example.neontest.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.neontest.R
import com.example.neontest.data.Result
import com.example.neontest.data.source.DigitalAccountRepository
import com.example.neontest.ui.BaseViewModel
import com.example.neontest.ui.transfer.ContactItem
import com.example.neontest.ui.transfer.toContactItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransferHistoryViewModel @Inject constructor(
    private val digitalAccountRepository: DigitalAccountRepository
) : BaseViewModel() {

    private val _transfers = MutableLiveData<List<ContactItem>>()
    val transfers: LiveData<List<ContactItem>>
        get() = _transfers

    fun getTransfers()  = viewModelScope.launchWithLoading(Dispatchers.IO) {
        val transfersResult = digitalAccountRepository.getTransfers()
        val contactsResult = digitalAccountRepository.getContacts()
        if (transfersResult is Result.Success && contactsResult is Result.Success) {
            transfersResult.data.map { transfer ->
                val contact = contactsResult.data.first { transfer.clientId == it.id }
                contact.toContactItem(transfer.value)
            }.also {
                withContext(Dispatchers.Main) {
                    _transfers.value = it
                }
            }
        } else {
            _toastText.value = R.string.error_getting_transfers
        }
    }
}