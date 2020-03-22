package com.example.neontest.di

import android.content.Context
import com.example.neontest.NeonApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        HomeModule::class,
        TransferModule::class,
        TransferHistoryModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<NeonApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}