package br.com.vicentec12.mygames.ui.splash.di

import br.com.vicentec12.mygames.di.ActivityScope
import br.com.vicentec12.mygames.ui.splash.SplashActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): SplashComponent

    }

    fun inject(mSplashActivity: SplashActivity)

}