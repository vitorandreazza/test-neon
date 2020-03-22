package com.example.neontest.di

import androidx.lifecycle.ViewModel
import com.example.neontest.ui.transfer.SendMoneyDialog
import com.example.neontest.ui.transfer.TransferFragment
import com.example.neontest.ui.transfer.TransferViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TransferModule {

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    abstract fun transferFragment(): TransferFragment

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    abstract fun sendMoneyDialogFragment(): SendMoneyDialog

    @Binds
    @IntoMap
    @ViewModelKey(TransferViewModel::class)
    abstract fun bindTransferViewModel(transferViewModel: TransferViewModel): ViewModel
}