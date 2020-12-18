package br.com.vicentec12.mygames.ui.console;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;

public class ConsoleViewModelFactory implements ViewModelProvider.Factory {

    private ConsoleRepository mConsoleRepository;

    public ConsoleViewModelFactory(ConsoleRepository mConsoleRepository) {
        this.mConsoleRepository = mConsoleRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ConsoleViewModel(mConsoleRepository);
    }

}
