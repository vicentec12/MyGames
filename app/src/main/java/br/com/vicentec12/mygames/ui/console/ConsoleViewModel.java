package br.com.vicentec12.mygames.ui.console;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource;

public class ConsoleViewModel extends ViewModel {

    private final int CHILD_PROGRESS = 0;
    private final int CHILD_RECYCLER = 1;
    private final int CHILD_TEXT = 2;

    private final MutableLiveData<Integer> _message = new MutableLiveData<>();
    private final MutableLiveData<Integer> _viewFlipperChild = new MutableLiveData<>();
    private final MutableLiveData<List<ConsoleWithGames>> _consoles = new MutableLiveData<>();

    private final ConsoleDataSource mConsoleRepository;

    @Inject
    public ConsoleViewModel(ConsoleDataSource mConsoleRepository) {
        this.mConsoleRepository = mConsoleRepository;
    }

    public void listConsoles() {
        _viewFlipperChild.setValue(CHILD_PROGRESS);
        mConsoleRepository.listConsolesWithGames(new ConsoleDataSource.OnConsolesWithGamesListedCallback() {
            @Override
            public void onSucess(int message, List<ConsoleWithGames> consolesWithGames) {
                _consoles.setValue(consolesWithGames);
                _viewFlipperChild.setValue(CHILD_RECYCLER);
            }

            @Override
            public void onErro(int message) {
                _message.setValue(message);
                _viewFlipperChild.setValue(CHILD_TEXT);
            }
        });
    }

    public MutableLiveData<List<ConsoleWithGames>> getConsoles() {
        return _consoles;
    }

    public MutableLiveData<Integer> getViewFlipperChild() {
        return _viewFlipperChild;
    }

    public MutableLiveData<Integer> getMessage() {
        return _message;
    }

}
