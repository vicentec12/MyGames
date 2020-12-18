package br.com.vicentec12.mygames.ui.console;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource;
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;
import br.com.vicentec12.mygames.extensions.Event;

public class ConsoleViewModel extends ViewModel {

    private ConsoleRepository mConsoleRepository;

    private MutableLiveData<List<ConsoleWithGames>> mMutableConsoles = new MutableLiveData<>();
    private MutableLiveData<Event<Integer>> mMessage = new MutableLiveData<>();

    public ConsoleViewModel(ConsoleRepository mConsoleRepository) {
        this.mConsoleRepository = mConsoleRepository;
    }

    public void listConsoles() {
        mConsoleRepository.listConsolesWithGames(new ConsoleDataSource.OnConsolesListedCallback() {
            @Override
            public void onSucess(int message, List<ConsoleWithGames> consolesWithGames) {
                mMessage.setValue(new Event<>(message));
                mMutableConsoles.setValue(consolesWithGames);
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

}
