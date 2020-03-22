package com.example.neontest.di

import androidx.lifecycle.ViewModelProvider
import com.example.neontest.utils.NeonViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: NeonViewModelFactory): ViewModelProvider.Factory
}