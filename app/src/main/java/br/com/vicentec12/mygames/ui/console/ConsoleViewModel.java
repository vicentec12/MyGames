package br.com.vicentec12.mygames.ui.console;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource;
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;

public class ConsoleViewModel extends ViewModel {

    private final int CHILD_PROGRESS = 0;
    private final int CHILD_RECYCLER = 1;
    private final int CHILD_TEXT = 2;

    private ConsoleRepository mConsoleRepository;

    private MutableLiveData<Integer> mMessageLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mViewFlipperLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ConsoleWithGames>> mConsolesLiveData = new MutableLiveData<>();

    public ConsoleViewModel(ConsoleRepository mConsoleRepository) {
        this.mConsoleRepository = mConsoleRepository;
    }

    public void listConsoles() {
        mViewFlipperLiveData.setValue(CHILD_PROGRESS);
        mConsoleRepository.listConsolesWithGames(new ConsoleDataSource.OnConsolesWithGamesListedCallback() {
            @Override
            public void onSucess(int message, List<ConsoleWithGames> consolesWithGames) {
                mConsolesLiveData.setValue(consolesWithGames);
                mViewFlipperLiveData.setValue(CHILD_RECYCLER);
            }

            @Override
            public void onErro(int message) {
                mMessageLiveData.setValue(message);
                mViewFlipperLiveData.setValue(CHILD_TEXT);
            }
        });
    }

    public MutableLiveData<List<ConsoleWithGames>> getConsolesLiveData() {
        return mConsolesLiveData;
    }

    public MutableLiveData<Integer> getViewFlipperLiveData() {
        return mViewFlipperLiveData;
    }

    public MutableLiveData<Integer> getMessageLiveData() {
        return mMessageLiveData;
    }

}
