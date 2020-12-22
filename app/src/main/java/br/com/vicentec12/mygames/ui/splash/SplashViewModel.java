package br.com.vicentec12.mygames.ui.splash;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource;
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;
import br.com.vicentec12.mygames.extensions.Event;
import br.com.vicentec12.mygames.interfaces.Callbacks;

public class SplashViewModel extends ViewModel {

    private ConsoleRepository mConsoleRepository;

    private MutableLiveData<Event<Boolean>> mHasFinishLiveData = new MutableLiveData<>();
    private MutableLiveData<Event<Integer>> mMessageLiveData = new MutableLiveData<>();

    public SplashViewModel(ConsoleRepository mConsoleRepository) {
        this.mConsoleRepository = mConsoleRepository;
    }

    public void initSplash() {
        Handler handle = new Handler();
        handle.postDelayed(this::loadOrCreateConsoles, 2000);
    }

    public void loadOrCreateConsoles() {
        mConsoleRepository.listConsolesWithGames(new ConsoleDataSource.OnConsolesWithGamesListedCallback() {
            @Override
            public void onSucess(int message, List<ConsoleWithGames> consolesWithGames) {
                mHasFinishLiveData.setValue(new Event<>(true));
            }

            @Override
            public void onErro(int message) {
                List<Console> consoles = new ArrayList<>();
                consoles.add(new Console("Nintendo", R.drawable.lg_nes));
                consoles.add(new Console("Super Nintendo", R.drawable.lg_snes));
                consoles.add(new Console("Nintendo 64", R.drawable.lg_n64));
                consoles.add(new Console("Nintendo Gamecube", R.drawable.lg_gc));
                consoles.add(new Console("Nintendo Wii", R.drawable.lg_wii));
                consoles.add(new Console("Nintendo 3DS", R.drawable.lg_3ds));
                consoles.add(new Console("Nintendo DS", R.drawable.lg_nds));
                consoles.add(new Console("Nintendo Switch", R.drawable.lg_switch));
                consoles.add(new Console("Playstation", R.drawable.lg_ps1));
                consoles.add(new Console("Playstation 2", R.drawable.lg_ps2));
                consoles.add(new Console("Playstation 3", R.drawable.lg_ps3));
                consoles.add(new Console("Playstation 4", R.drawable.lg_ps4));
                consoles.add(new Console("Xbox", R.drawable.lg_xbox));
                consoles.add(new Console("Xbox 360", R.drawable.lg_x360));
                consoles.add(new Console("Xbox One", R.drawable.lg_xone));
                mConsoleRepository.insertConsoles(consoles, new Callbacks.OnLocalCallback() {
                    @Override
                    public void onSuccess(int mMessage) {
                        mHasFinishLiveData.setValue(new Event<>(true));
                    }

                    @Override
                    public void onFailure(int mMessage) {
                        mMessageLiveData.setValue(new Event<>(mMessage));
                    }
                });
            }
        });
    }

    public MutableLiveData<Event<Boolean>> getHasFinishMutable() {
        return mHasFinishLiveData;
    }

    public MutableLiveData<Event<Integer>> getMessageLiveData() {
        return mMessageLiveData;
    }

}
