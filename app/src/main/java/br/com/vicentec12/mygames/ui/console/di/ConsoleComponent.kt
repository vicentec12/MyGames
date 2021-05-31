package br.com.vicentec12.mygames.ui.console.di

import br.com.vicentec12.mygames.di.ActivityScope
import br.com.vicentec12.mygames.ui.console.ConsoleActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ConsoleModule::class])
interface ConsoleComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ConsoleComponent
    }

    fun inject(mConsoleActivity: ConsoleActivity)

}