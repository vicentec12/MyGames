package br.com.vicentec12.mygames.presentation.game;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameViewModelTest {

//    @Rule
//    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();
//
//    @Mock
//    private GameRepository mGameRepository;
//
//    @Mock
//    private Observer<List<Game>> mGamesLiveDataObserver;
//
//    @Mock
//    private Observer<Integer> mViewFlipperLiveDataObserver;
//
//    @InjectMocks
//    private GameViewModel mGameViewModel;
//
//    @Captor
//    private ArgumentCaptor<GameDataSource.OnGamesListedCallback> mOnGamesListedCallbackCaptor;
//
//    @Test
//    public void listSavedGamesSuccessTest() {
//        // Arrange
//        Console mConsole = new Console(1, "Console 1", 1);
//        List<Game> games = Arrays.asList(new Game(1, "Game 1", "1991"), new Game(2, "Game 2", "1992"),
//                new Game(3, "Game 3", "1993"), new Game(4, "Game 4", "1994")
//        );
//        mGameViewModel.getConsole().setValue(mConsole);
//        mGameViewModel.getOrderBy().setValue(1);
//        mGameViewModel.getGames().observeForever(mGamesLiveDataObserver);
//        mGameViewModel.getViewFlipper().observeForever(mViewFlipperLiveDataObserver);
//
//        // Act
//        mGameViewModel.listSavedGames();
//        verify(mGameRepository).list(eq(mConsole), eq(1), mOnGamesListedCallbackCaptor.capture());
//        mOnGamesListedCallbackCaptor.getValue().onSuccess(games);
//
//        //Assert
//        verify(mGamesLiveDataObserver).onChanged(games);
//        verify(mViewFlipperLiveDataObserver).onChanged(1);
//    }

}