package br.com.vicentec12.mygames.ui.game;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.vicentec12.mygames.data.source.game.GameRepository;

public class GameViewModelFactory implements ViewModelProvider.Factory {

    private final GameRepository mGameRepository;

    public GameViewModelFactory(GameRepository mGameRepository) {
        this.mGameRepository = mGameRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GameViewModel(mGameRepository);
    }

}
