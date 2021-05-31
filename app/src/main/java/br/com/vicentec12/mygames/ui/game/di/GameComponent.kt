package br.com.vicentec12.mygames.ui.game.di

import br.com.vicentec12.mygames.di.ActivityScope
import br.com.vicentec12.mygames.ui.game.GameActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [GameModule::class])
interface GameComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): GameComponent

    }

    fun inject(mGameActivity: GameActivity)

}