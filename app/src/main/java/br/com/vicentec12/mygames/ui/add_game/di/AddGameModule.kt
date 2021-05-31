package br.com.vicentec12.mygames.ui.add_game.di

import androidx.lifecycle.ViewModel
import br.com.vicentec12.mygames.di.ActivityScope
import br.com.vicentec12.mygames.di.ViewModelKey
import br.com.vicentec12.mygames.ui.add_game.AddGameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddGameModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(AddGameViewModel::class)
    abstract fun bindAddGameViewModel(mViewModel: AddGameViewModel): ViewModel

}