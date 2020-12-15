package br.com.vicentec12.mygames.ui.add_game;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.game.GameRepository;
import br.com.vicentec12.mygames.extensions.Event;
import br.com.vicentec12.mygames.interfaces.Callbacks;

public class AddGameViewModel extends ViewModel {

    private final MutableLiveData<Game> mMutableGame = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> mMutableMessage = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> mMutableEventDatabase = new MutableLiveData<>();

    private GameRepository mGameRepository;

    public AddGameViewModel(GameRepository mGameRepository) {
        this.mGameRepository = mGameRepository;
    }

    public void setGame(Game game) {
        if (game == null)
            game = new Game();
        mMutableGame.setValue(game);
    }

    public void databaseEvent() {
        Game game = getMutableGame().getValue();
        if (game != null) {
            if (game.getId() > 0)
                updateGame(game);
            else
                insertGame(game);
        }
    }

    private void insertGame(Game game) {
        mGameRepository.insert(game, new Callbacks.OnLocalCallback() {
            @Override
            public void onSuccess(int mMessage) {
                mMutableEventDatabase.setValue(new Event<>(true));
                mMutableMessage.setValue(new Event<>(mMessage));
            }

            @Override
            public void onFailure(int mMessage) {
                mMutableMessage.setValue(new Event<>(mMessage));
            }
        });
    }

    private void updateGame(Game game) {
        mGameRepository.update(game, new Callbacks.OnLocalCallback() {
            @Override
            public void onSuccess(int mMessage) {
                mMutableEventDatabase.setValue(new Event<>(true));
                mMutableMessage.setValue(new Event<>(mMessage));
            }

            @Override
            public void onFailure(int mMessage) {
                mMutableMessage.setValue(new Event<>(mMessage));
            }
        });
    }

    public boolean hasInsert() {
        if (mMutableGame.getValue() != null)
            return mMutableGame.getValue().getId() == 0;
        return false;
    }

    public MutableLiveData<Game> getMutableGame() {
        return mMutableGame;
    }

    public MutableLiveData<Event<Boolean>> getMutableEventDatabase() {
        return mMutableEventDatabase;
    }

    public MutableLiveData<Event<Integer>> getMutableMessage() {
        return mMutableMessage;
    }

    public static class AddGameViewModelFactory implements ViewModelProvider.Factory {

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

}
