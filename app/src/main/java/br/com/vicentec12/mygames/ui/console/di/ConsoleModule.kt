package br.com.vicentec12.mygames.ui.console.di

import androidx.lifecycle.ViewModel
import br.com.vicentec12.mygames.di.ActivityScope
import br.com.vicentec12.mygames.di.ViewModelKey
import br.com.vicentec12.mygames.ui.console.ConsoleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ConsoleModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(ConsoleViewModel::class)
    abstract fun bindConsoleViewModel(mViewModel: ConsoleViewModel): ViewModel

}