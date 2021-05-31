package br.com.vicentec12.mygames.ui.splash.di

import androidx.lifecycle.ViewModel
import br.com.vicentec12.mygames.di.ActivityScope
import br.com.vicentec12.mygames.di.ViewModelKey
import br.com.vicentec12.mygames.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(mViewModel: SplashViewModel): ViewModel

}