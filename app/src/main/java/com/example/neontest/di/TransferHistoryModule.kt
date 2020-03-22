package com.example.neontest.di

import androidx.lifecycle.ViewModel
import com.example.neontest.ui.history.TransferHistoryFragment
import com.example.neontest.ui.history.TransferHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TransferHistoryModule {

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    abstract fun transferHistoryFragment(): TransferHistoryFragment

    @Binds
    @IntoMap
    @ViewModelKey(TransferHistoryViewModel::class)
    abstract fun bindTransferHistoryViewModel(transferHistoryViewModel: TransferHistoryViewModel): ViewModel
}