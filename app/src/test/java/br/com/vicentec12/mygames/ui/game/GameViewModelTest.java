package br.com.vicentec12.mygames.ui.game;

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
import br.com.vicentec12.mygames.data.source.game.GameDataSource;
import br.com.vicentec12.mygames.data.source.game.GameRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameViewModelTest {

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private GameRepository mGameRepository;

    @Mock
    private Observer<List<Game>> mGamesLiveDataObserver;

    @Mock
    private Observer<Integer> mViewFlipperLiveDataObserver;

    @InjectMocks
    private GameViewModel mGameViewModel;

    @Captor
    private ArgumentCaptor<GameDataSource.OnGamesListedCallback> mOnGamesListedCallbackCaptor;

    @Test
    public void listSavedGamesSuccessTest() {
        // Arrange
        Console mConsole = new Console(1, "Console 1", 1);
        List<Game> games = Arrays.asList(new Game(1, "Game 1", "1991"), new Game(2, "Game 2", "1992"),
                new Game(3, "Game 3", "1993"), new Game(4, "Game 4", "1994")
        );
        mGameViewModel.getConsoleLiveData().setValue(mConsole);
        mGameViewModel.getOrderByLiveData().setValue(1);
        mGameViewModel.getGamesLiveData().observeForever(mGamesLiveDataObserver);
        mGameViewModel.getViewFlipperLiveData().observeForever(mViewFlipperLiveDataObserver);

        // Act
        mGameViewModel.listSavedGames();
        verify(mGameRepository).list(eq(mConsole), eq(1), mOnGamesListedCallbackCaptor.capture());
        mOnGamesListedCallbackCaptor.getValue().onSuccess(games);

        //Assert
        verify(mGamesLiveDataObserver).onChanged(games);
        verify(mViewFlipperLiveDataObserver).onChanged(1);
    }

}