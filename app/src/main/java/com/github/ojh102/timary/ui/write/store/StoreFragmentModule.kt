package com.github.ojh102.timary.ui.write.store

import androidx.lifecycle.ViewModel
import com.github.ojh102.timary.annotation.ViewModelKey
import com.github.ojh102.timary.annotation.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [StoreFragmentModule.ProvideModule::class])
internal interface StoreFragmentModule {
    @Module
    class ProvideModule

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    fun bindViewModel(viewModel: StoreViewModel): ViewModel
}
