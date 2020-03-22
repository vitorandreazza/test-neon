package com.example.neontest.ui.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.example.neontest.databinding.DialogSendMoneyBinding
import com.example.neontest.extensions.observeToast
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.dialog_send_money.*
import javax.inject.Inject

class SendMoneyDialog : DaggerDialogFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val transferViewModel: TransferViewModel by viewModels { viewModelFactory }
    private val args: SendMoneyDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogSendMoneyBinding.inflate(inflater, container, false).apply {
            viewModel = transferViewModel
            contact = args.contact
            moneyEditText = edittextTransferValue
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_close.setOnClickListener {
            requireActivity().onBackPressed()
        }
        transferViewModel.moneySent.observe(viewLifecycleOwner) { moneySent ->
            if (moneySent) requireActivity().onBackPressed()
        }
        transferViewModel.isLoading.observe(viewLifecycleOwner) {
            loading.isVisible = it
            button_transfer.isInvisible = it
        }
        observeToast(transferViewModel)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
