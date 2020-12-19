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

    private final MutableLiveData<Game> mMutableGame = new MutableLiveData<>();
    private final MutableLiveData<List<Console>> mMutableConsoles = new MutableLiveData<>();
    private final MutableLiveData<List<Integer>> mMutableSpinnerPosition = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> mMutableMessage = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> mMutableEventDatabase = new MutableLiveData<>();

    private GameRepository mGameRepository;
    private ConsoleRepository mConsoleRepository;

    public AddGameViewModel(GameRepository mGameRepository, ConsoleRepository mConsoleRepository) {
        this.mGameRepository = mGameRepository;
        this.mConsoleRepository = mConsoleRepository;
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

    public void listConsoles(Game mGame) {
        if (mMutableConsoles.getValue() == null) {
            mConsoleRepository.listConsoles(new ConsoleDataSource.OnConsolesListedCallback() {
                @Override
                public void onSucess(int message, List<Console> consoles) {
                    mMutableConsoles.setValue(consoles);
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

    public void setConsoleSelection() {
        if (mMutableGame.getValue() != null && mMutableGame.getValue().getIdConsole() > 0) {

        }
    }

    public void setConsoleGame(Console console) {
        if (mMutableGame.getValue() != null) {
            Game game = mMutableGame.getValue();
            game.setIdConsole(console.getId());
            mMutableGame.setValue(game);
        }
    }

    public boolean hasInsert() {
        if (mMutableGame.getValue() != null)
            return mMutableGame.getValue().getId() == 0;
        return false;
    }

    public void setGame(Game game) {
        if (mMutableGame.getValue() == null)
            mMutableGame.postValue(game != null ? game : new Game());
    }

    public MutableLiveData<Game> getMutableGame() {
        return mMutableGame;
    }

    public MutableLiveData<List<Console>> getMutableConsoles() {
        return mMutableConsoles;
    }

    public MutableLiveData<Event<Boolean>> getMutableEventDatabase() {
        return mMutableEventDatabase;
    }

    public MutableLiveData<Event<Integer>> getMutableMessage() {
        return mMutableMessage;
    }

    public MutableLiveData<List<Integer>> getMutableSpinnerPosition() {
        return mMutableSpinnerPosition;
    }

}
