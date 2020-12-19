package br.com.vicentec12.mygames.ui.console;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource;
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;
import br.com.vicentec12.mygames.extensions.Event;

public class ConsoleViewModel extends ViewModel {

    private final int CHILD_PROGRESS = 0;
    private final int CHILD_RECYCLER = 1;
    private final int CHILD_TEXT = 2;

    private ConsoleRepository mConsoleRepository;

    private MutableLiveData<Event<Integer>> mMessage = new MutableLiveData<>();
    private MutableLiveData<Integer> mMutableViewFlipperChild = new MutableLiveData<>();
    private MutableLiveData<List<ConsoleWithGames>> mMutableConsoles = new MutableLiveData<>();

    public ConsoleViewModel(ConsoleRepository mConsoleRepository) {
        this.mConsoleRepository = mConsoleRepository;
    }

    public void listConsoles() {
        mMutableViewFlipperChild.setValue(CHILD_PROGRESS);
        mConsoleRepository.listConsolesWithGames(new ConsoleDataSource.OnConsolesWithGamesListedCallback() {
            @Override
            public void onSucess(int message, List<ConsoleWithGames> consolesWithGames) {
                mMessage.setValue(new Event<>(message));
                mMutableConsoles.setValue(consolesWithGames);
                mMutableViewFlipperChild.setValue(CHILD_RECYCLER);
            }

            @Override
            public void onErro(int message) {
                mMessage.setValue(new Event<>(message));
            }
        });
    }

    public MutableLiveData<List<ConsoleWithGames>> getMutableConsoles() {
        return mMutableConsoles;
    }

    public MutableLiveData<Integer> getMutableViewFlipperChild() {
        return mMutableViewFlipperChild;
    }
}
