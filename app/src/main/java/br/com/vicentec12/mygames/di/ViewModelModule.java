package br.com.vicentec12.mygames.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.vicentec12.mygames.ui.add_game.AddGameViewModel;
import br.com.vicentec12.mygames.ui.console.ConsoleViewModel;
import br.com.vicentec12.mygames.ui.game.GameViewModel;
import br.com.vicentec12.mygames.ui.splash.SplashViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(value = AddGameViewModel.class)
    abstract ViewModel bindAddGameViewModel(AddGameViewModel mAddGameViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(value = ConsoleViewModel.class)
    abstract ViewModel bindConsoleViewModel(ConsoleViewModel mConsoleViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(value = GameViewModel.class)
    abstract ViewModel bindGameViewModel(GameViewModel mGameViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(value = SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel mSplashViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelProviderFactory(ViewModelProviderFactory mFactory);

}
