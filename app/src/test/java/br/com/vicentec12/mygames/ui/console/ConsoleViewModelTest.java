package br.com.vicentec12.mygames.ui.console;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
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

import br.com.vicentec12.mygames.R;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleViewModelTest {

    @Rule
    public InstantTaskExecutorRule mInstantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ConsoleRepository mConsoleRepository;

    @Mock
    private Observer<List<ConsoleWithGames>> mConsolesLiveDataObserver;

    @Mock
    private Observer<Integer> mViewFLipperLiveDataObserver;

    @Mock
    private Observer<Integer> mMessageLiveDataObserver;

    @InjectMocks
    private ConsoleViewModel mConsoleViewModel;

    @Captor
    private ArgumentCaptor<ConsoleDataSource.OnConsolesWithGamesListedCallback> mCallbackCaptor;

    private List<ConsoleWithGames> mConsoles;

    @Before
    public void setUp() {
        Console mConsole1 = new Console("Teste 1", 1);
        Console mConsole2 = new Console("Teste 2", 1);
        List<Game> mGames1 = Arrays.asList(
                new Game("Teste 1", "1991"),
                new Game("Teste 2", "1992")
        );
        List<Game> mGames2 = Arrays.asList(
                new Game("Teste 3", "1991"),
                new Game("Teste 4", "1992")
        );
        ConsoleWithGames consoleWithGames1 = new ConsoleWithGames();
        consoleWithGames1.setConsole(mConsole1);
        consoleWithGames1.setGames(mGames1);
        ConsoleWithGames consoleWithGames2 = new ConsoleWithGames();
        consoleWithGames2.setConsole(mConsole2);
        consoleWithGames2.setGames(mGames2);
        mConsoles = Arrays.asList(consoleWithGames1, consoleWithGames2);
    }

    @Test
    public void listConsolesSuccessTest() {
        // Arange
        int message = R.string.message_consoles_listed;
        mConsoleViewModel.getConsoles().observeForever(mConsolesLiveDataObserver);
        mConsoleViewModel.getViewFlipperChild().observeForever(mViewFLipperLiveDataObserver);

        // Act
        mConsoleViewModel.listConsoles();
        verify(mConsoleRepository).listConsolesWithGames(mCallbackCaptor.capture());
        mCallbackCaptor.getValue().onSucess(message, mConsoles);

        //Assert
        verify(mConsolesLiveDataObserver).onChanged(mConsoles);
        verify(mViewFLipperLiveDataObserver).onChanged(1);
    }

    @Test
    public void listConsolesErrorTest() {
        // Arrange
        int message = R.string.message_consoles_empty;
        mConsoleViewModel.getMessage().observeForever(mMessageLiveDataObserver);
        mConsoleViewModel.getViewFlipperChild().observeForever(mViewFLipperLiveDataObserver);

        // Act
        mConsoleViewModel.listConsoles();
        verify(mConsoleRepository).listConsolesWithGames(mCallbackCaptor.capture());
        mCallbackCaptor.getValue().onErro(message);

        // Assert
        verify(mMessageLiveDataObserver).onChanged(message);
        verify(mViewFLipperLiveDataObserver).onChanged(2);
    }

}
