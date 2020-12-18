package br.com.vicentec12.mygames.ui.splash;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;

public class SplashViewModelFactory implements ViewModelProvider.Factory {

    private ConsoleRepository mConsoleRepository;

    public SplashViewModelFactory(ConsoleRepository mConsoleRepository) {
        this.mConsoleRepository = mConsoleRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SplashViewModel(mConsoleRepository);
    }

}
