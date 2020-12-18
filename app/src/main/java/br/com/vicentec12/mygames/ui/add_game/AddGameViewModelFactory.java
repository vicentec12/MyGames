package br.com.vicentec12.mygames.ui.add_game;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.vicentec12.mygames.data.source.game.GameRepository;

public class AddGameViewModelFactory implements ViewModelProvider.Factory {

    private GameRepository mGameRepository;

    public AddGameViewModelFactory(GameRepository mGameRepository) {
        this.mGameRepository = mGameRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddGameViewModel(mGameRepository);
    }

}
