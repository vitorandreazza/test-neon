package com.example.neontest.ui.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.neontest.R
import com.example.neontest.data.Result
import com.example.neontest.data.source.DigitalAccountRepository
import com.example.neontest.extensions.exhaustive
import com.example.neontest.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransferViewModel @Inject constructor(
    private val digitalAccountRepository: DigitalAccountRepository
) : BaseViewModel() {

    private val _contacts = MutableLiveData<List<ContactItem>>()
    val contacts: LiveData<List<ContactItem>>
        get() = _contacts
    private val _moneySent = MutableLiveData<Boolean>()
    val moneySent: LiveData<Boolean> = _moneySent

    fun getContacts() {
        viewModelScope.launchWithLoading(Dispatchers.IO) {
            val result = digitalAccountRepository.getContacts()
            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    val items = result.data
                        .map {
                            it.toContactItem().apply {
                                val directions =
                                    TransferFragmentDirections.actionTransferFragmentToSendMoneyDialog(
                                        this
                                    )
                                click = Navigation.createNavigateOnClickListener(directions)
                            }
                        }
                    _contacts.value = items
                }
            }
        }
    }

    fun transferMoney(contact: ContactItem, value: Float?) {
        if (value == null || value == 0f) {
            _toastText.value = R.string.error_transfer_value_nedeed
            return
        }
        sendMoney(contact, value)
    }

    private fun sendMoney(contact: ContactItem, value: Float) {
        viewModelScope.launchWithLoading(Dispatchers.IO) {
            val result = digitalAccountRepository.sendMoney(contact, value)
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        _toastText.value = R.string.message_transfer_success
                        _moneySent.value = true
                    }
                    is Result.Error -> _toastText.value = R.string.message_transfer_error
                }.exhaustive
            }
        }
    }
}

