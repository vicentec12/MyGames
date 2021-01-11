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

    private final MutableLiveData<Game> _game = new MutableLiveData<>();
    private final MutableLiveData<List<Console>> _consoles = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> _message = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> _eventDatabase = new MutableLiveData<>();

    private final GameRepository mGameRepository;
    private final ConsoleRepository mConsoleRepository;


    public AddGameViewModel(GameRepository mGameRepository, ConsoleRepository mConsoleRepository) {
        this.mGameRepository = mGameRepository;
        this.mConsoleRepository = mConsoleRepository;
    }

    public void databaseEvent() {
        Game game = getGame().getValue();
        if (game != null) {
            if (game.getId() > 0)
                updateGame(game);
            else
                insertGame(game);
        }
    }

    public void listConsoles(Game mGame) {
        if (_consoles.getValue() == null) {
            mConsoleRepository.listConsoles(new ConsoleDataSource.OnConsolesListedCallback() {
                @Override
                public void onSucess(int message, List<Console> consoles) {
                    _consoles.setValue(consoles);
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
                _eventDatabase.setValue(new Event<>(true));
                _message.setValue(new Event<>(mMessage));
            }

            @Override
            public void onFailure(int mMessage) {
                _message.setValue(new Event<>(mMessage));
            }
        });
    }

    private void updateGame(Game game) {
        mGameRepository.update(game, new Callbacks.OnLocalCallback() {
            @Override
            public void onSuccess(int mMessage) {
                _eventDatabase.setValue(new Event<>(true));
                _message.setValue(new Event<>(mMessage));
            }

            @Override
            public void onFailure(int mMessage) {
                _message.setValue(new Event<>(mMessage));
            }
        });
    }

    public boolean hasInsert() {
        if (_game.getValue() != null)
            return _game.getValue().getId() == 0;
        return false;
    }

    public void setGame(Game game) {
        if (_game.getValue() == null)
            _game.postValue(game != null ? game : new Game());
    }

    public MutableLiveData<Game> getGame() {
        return _game;
    }

    public MutableLiveData<List<Console>> getConsoles() {
        return _consoles;
    }

    public MutableLiveData<Event<Boolean>> getEventDatabase() {
        return _eventDatabase;
    }

    public MutableLiveData<Event<Integer>> getMessage() {
        return _message;
    }

}
