package br.com.vicentec12.mygames.ui.add_game;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource;
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;
import br.com.vicentec12.mygames.data.source.game.GameRepository;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddGameViewModelTest {

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ConsoleRepository mConsoleRepository;

    @Mock
    private Observer<List<Console>> mConsolesLiveDataObserver;

    @Mock
    private Observer<Game> mGameLiveDataObserver;

    @InjectMocks
    private AddGameViewModel mAddGameViewModel;

    @Captor
    private ArgumentCaptor<ConsoleDataSource.OnConsolesListedCallback> mOnConsolesListedCallbackCaptor;

    @Test
    public void listConsolesSuccessTest() {
        Game mGame = new Game("Game 1", "1991");
        List<Console> consoles = Arrays.asList(new Console("Console 1", 1),
                new Console("Console 2", 2), new Console("Console 3", 3),
                new Console("Console 4", 4), new Console("Console 5", 5)
        );
        mAddGameViewModel.getGameLiveData().observeForever(mGameLiveDataObserver);
        mAddGameViewModel.getConsolesLiveData().observeForever(mConsolesLiveDataObserver);

        // Act
        mAddGameViewModel.listConsoles(mGame);
        verify(mConsoleRepository).listConsoles(mOnConsolesListedCallbackCaptor.capture());
        mOnConsolesListedCallbackCaptor.getValue().onSucess(1, consoles);

        // Assert
        verify(mGameLiveDataObserver).onChanged(mGame);
        verify(mConsolesLiveDataObserver).onChanged(consoles);
    }

}