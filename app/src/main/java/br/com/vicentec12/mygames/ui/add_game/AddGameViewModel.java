package br.com.vicentec12.mygames.ui.add_game;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource;
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;
import br.com.vicentec12.mygames.data.source.game.GameRepository;
import br.com.vicentec12.mygames.extensions.Event;
import br.com.vicentec12.mygames.interfaces.Callbacks;

public class AddGameViewModel extends ViewModel {

    private GameRepository mGameRepository;
    private ConsoleRepository mConsoleRepository;

    private final MutableLiveData<Game> mGameLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Console>> mConsolesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> mMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> mEventDatabaseLiveData = new MutableLiveData<>();

    public AddGameViewModel(GameRepository mGameRepository, ConsoleRepository mConsoleRepository) {
        this.mGameRepository = mGameRepository;
        this.mConsoleRepository = mConsoleRepository;
    }

    public void databaseEvent() {
        Game game = getGameLiveData().getValue();
        if (game != null) {
            if (game.getId() > 0)
                updateGame(game);
            else
                insertGame(game);
        }
    }

    public void listConsoles(Game mGame) {
        if (mConsolesLiveData.getValue() == null) {
            mConsoleRepository.listConsoles(new ConsoleDataSource.OnConsolesListedCallback() {
                @Override
                public void onSucess(int message, List<Console> consoles) {
                    mConsolesLiveData.setValue(consoles);
                    setGame(mGame);
                }

                @Override
                public void onErro(int message) {

                }
            });
        }
    }

    private void insertGame(Game game) {
        mGameRepository.insert(game, new Callbacks.OnLocalCallback() {
            @Override
            public void onSuccess(int mMessage) {
                mEventDatabaseLiveData.setValue(new Event<>(true));
                mMessageLiveData.setValue(new Event<>(mMessage));
            }

            @Override
            public void onFailure(int mMessage) {
                mMessageLiveData.setValue(new Event<>(mMessage));
            }
        });
    }

    private void updateGame(Game game) {
        mGameRepository.update(game, new Callbacks.OnLocalCallback() {
            @Override
            public void onSuccess(int mMessage) {
                mEventDatabaseLiveData.setValue(new Event<>(true));
                mMessageLiveData.setValue(new Event<>(mMessage));
            }

            @Override
            public void onFailure(int mMessage) {
                mMessageLiveData.setValue(new Event<>(mMessage));
            }
        });
    }

    public boolean hasInsert() {
        if (mGameLiveData.getValue() != null)
            return mGameLiveData.getValue().getId() == 0;
        return false;
    }

    public void setGame(Game game) {
        if (mGameLiveData.getValue() == null)
            mGameLiveData.postValue(game != null ? game : new Game());
    }

    public MutableLiveData<Game> getGameLiveData() {
        return mGameLiveData;
    }

    public MutableLiveData<List<Console>> getConsolesLiveData() {
        return mConsolesLiveData;
    }

    public MutableLiveData<Event<Boolean>> getEventDatabaseLiveData() {
        return mEventDatabaseLiveData;
    }

    public MutableLiveData<Event<Integer>> getMessageLiveData() {
        return mMessageLiveData;
    }

}
