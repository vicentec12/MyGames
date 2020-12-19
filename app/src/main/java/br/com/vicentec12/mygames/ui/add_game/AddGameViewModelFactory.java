package br.com.vicentec12.mygames.ui.add_game;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;
import br.com.vicentec12.mygames.data.source.game.GameRepository;

public class AddGameViewModelFactory implements ViewModelProvider.Factory {

    private GameRepository mGameRepository;
    private ConsoleRepository mConsoleRepository;

    public AddGameViewModelFactory(GameRepository mGameRepository, ConsoleRepository mConsoleRepository) {
        this.mGameRepository = mGameRepository;
        this.mConsoleRepository = mConsoleRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddGameViewModel(mGameRepository, mConsoleRepository);
    }

}
