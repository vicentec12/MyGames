package br.com.vicentec12.mygames.presentation.add_game;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddGameViewModelTest {

//    @Rule
//    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();
//
//    @Mock
//    private ConsoleRepository mConsoleRepository;
//
//    @Mock
//    private Observer<List<Console>> mConsolesLiveDataObserver;
//
//    @Mock
//    private Observer<Game> mGameLiveDataObserver;
//
//    @InjectMocks
//    private AddGameViewModel mAddGameViewModel;
//
//    @Captor
//    private ArgumentCaptor<ConsoleDataSource.OnConsolesListedCallback> mOnConsolesListedCallbackCaptor;
//
//    @Test
//    public void listConsolesSuccessTest() {
//        Game mGame = new Game("Game 1", "1991");
//        List<Console> consoles = Arrays.asList(new Console("Console 1", 1),
//                new Console("Console 2", 2), new Console("Console 3", 3),
//                new Console("Console 4", 4), new Console("Console 5", 5)
//        );
//        mAddGameViewModel.getGame().observeForever(mGameLiveDataObserver);
//        mAddGameViewModel.getConsoles().observeForever(mConsolesLiveDataObserver);
//
//        // Act
//        mAddGameViewModel.listConsoles(mGame);
//        verify(mConsoleRepository).listConsoles(mOnConsolesListedCallbackCaptor.capture());
//        mOnConsolesListedCallbackCaptor.getValue().onSucess(1, consoles);
//
//        // Assert
//        verify(mGameLiveDataObserver).onChanged(mGame);
//        verify(mConsolesLiveDataObserver).onChanged(consoles);
//    }

}