package com.example.neontest.ui.transfer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.neontest.*
import com.example.neontest.data.Result
import com.example.neontest.data.source.DigitalAccountRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class TransferViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val digitalAccountRepository: DigitalAccountRepository = mock()
    private val transferViewModel = TransferViewModel(digitalAccountRepository)
    private val transferValue = 10f

    @Test
    fun `should show transfer succeed`() = runBlockingTest {
        whenever(digitalAccountRepository.sendMoney(CONTACT_ITEM1, transferValue))
            .thenReturn(Result.Success(true))

        transferViewModel.transferMoney(CONTACT_ITEM1, transferValue)

        transferViewModel.toastText.observeForTesting {
            assertThat(transferViewModel.toastText.getOrAwaitValue()).isEqualTo(R.string.message_transfer_success)
        }

        transferViewModel.moneySent.observeForTesting {
            assertThat(transferViewModel.moneySent.getOrAwaitValue()).isTrue()
        }
    }

    @Test
    fun `should show value input error if is 0`() = runBlockingTest {
        transferViewModel.transferMoney(CONTACT_ITEM1, 0f)

        transferViewModel.toastText.observeForTesting {
            assertThat(transferViewModel.toastText.getOrAwaitValue()).isEqualTo(R.string.error_transfer_value_nedeed)
        }
    }

    @Test
    fun `should show transfer error if fails`() = runBlockingTest {
        whenever(digitalAccountRepository.sendMoney(CONTACT_ITEM1, transferValue))
            .thenReturn(Result.Error(IOException()))

        transferViewModel.transferMoney(CONTACT_ITEM1, transferValue)

        transferViewModel.toastText.observeForTesting {
            assertThat(transferViewModel.toastText.getOrAwaitValue()).isEqualTo(R.string.message_transfer_error)
        }
    }
}