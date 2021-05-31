package br.com.vicentec12.mygames.ui.game.di

import androidx.lifecycle.ViewModel
import br.com.vicentec12.mygames.di.ActivityScope
import br.com.vicentec12.mygames.di.ViewModelKey
import br.com.vicentec12.mygames.ui.game.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class GameModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(mViewModel: GameViewModel): ViewModel

}