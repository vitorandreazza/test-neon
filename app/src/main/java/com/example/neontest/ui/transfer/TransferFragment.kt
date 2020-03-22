package com.example.neontest.ui.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neontest.R
import com.example.neontest.databinding.FragmentTransferBinding
import com.example.neontest.extensions.setupToolbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class TransferFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val transferViewModel: TransferViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTransferBinding.inflate(inflater, container, false)
            .apply {
                viewModel = transferViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        val adapter = ContactsAdapter()
        binding.recyclerviewContacts.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
        transferViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar(toolbar)
        toolbar_title.text = getString(R.string.title_transfer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transferViewModel.getContacts()
    }
}
