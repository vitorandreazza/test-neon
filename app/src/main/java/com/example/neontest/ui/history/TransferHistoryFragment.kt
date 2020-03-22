package com.example.neontest.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neontest.R
import com.example.neontest.databinding.FragmentTransferHistoryBinding
import com.example.neontest.extensions.observeToast
import com.example.neontest.extensions.setupToolbar
import com.example.neontest.ui.transfer.ContactsAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_transfer_history.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class TransferHistoryFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val transferHistoryViewModel: TransferHistoryViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTransferHistoryBinding.inflate(inflater, container, false)
            .apply {
                viewModel = transferHistoryViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar(toolbar)
        toolbar_title.text = getString(R.string.title_transfer_history)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()

        transferHistoryViewModel.getTransfers()
    }

    private fun initUi() {
        val transfersAdapter = ContactsAdapter()
        recyclerview_transfers.apply {
            this.adapter = transfersAdapter
            layoutManager = LinearLayoutManager(context)
        }
        val transfersGraphAdapter = TransfersGraphAdapter()
        recyclerview_graph_transfers.apply {
            this.adapter = transfersGraphAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        transferHistoryViewModel.transfers.observe(viewLifecycleOwner) { transfers ->
            transfersAdapter.submitList(transfers)
            transfersGraphAdapter.submitList(transfers)
        }
        observeToast(transferHistoryViewModel)
    }
}