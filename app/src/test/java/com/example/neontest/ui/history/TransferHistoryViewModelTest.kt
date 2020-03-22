package com.example.neontest.ui.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.neontest.*
import com.example.neontest.data.Result
import com.example.neontest.data.model.Contact
import com.example.neontest.data.model.Transfer
import com.example.neontest.data.source.DigitalAccountRepository
import com.example.neontest.ui.transfer.ContactItem
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.*

@ExperimentalCoroutinesApi
class TransferHistoryViewModelTest {


    @get:Rule val coroutinesTestRule = CoroutinesTestRule()
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    private val digitalAccountRepository: DigitalAccountRepository = mock()
    private val transferHistoryViewModel = TransferHistoryViewModel(digitalAccountRepository)

    @Test
    fun `should map contact and transfer`() = runBlockingTest {
        whenever(digitalAccountRepository.getTransfers()).thenReturn(Result.Success(TRANSFERS))
        whenever(digitalAccountRepository.getContacts()).thenReturn(Result.Success(CONTACTS))

        transferHistoryViewModel.getTransfers()

        transferHistoryViewModel.transfers.observeForTesting {
            assertThat(transferHistoryViewModel.transfers.getOrAwaitValue()).isEqualTo(CONTACT_ITEMS)
        }
    }

    @Test
    fun `should show error message when get transfers fail`() = runBlockingTest {
        whenever(digitalAccountRepository.getTransfers()).thenReturn(Result.Error(IOException()))
        whenever(digitalAccountRepository.getContacts()).thenReturn(Result.Success(CONTACTS))

        transferHistoryViewModel.getTransfers()

        transferHistoryViewModel.toastText.observeForTesting {
            assertThat(transferHistoryViewModel.toastText.getOrAwaitValue()).isEqualTo(R.string.error_getting_transfers)
        }
    }
}