package com.example.neontest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.neontest.R
import com.example.neontest.databinding.FragmentHomeBinding
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
            .apply {
                viewModel = homeViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel.generateToken()
        btn_transfer.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_home_fragment_to_transferFragment))
        btn_transfer_history.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_home_fragment_to_transferHistoryFragment))
    }
}