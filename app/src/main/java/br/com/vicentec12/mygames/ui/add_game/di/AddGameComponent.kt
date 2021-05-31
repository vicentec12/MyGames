package br.com.vicentec12.mygames.ui.add_game.di

import br.com.vicentec12.mygames.di.ActivityScope
import br.com.vicentec12.mygames.ui.add_game.AddGameActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [AddGameModule::class])
interface AddGameComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AddGameComponent
    }

    fun inject(mAddGameActivity: AddGameActivity)

}