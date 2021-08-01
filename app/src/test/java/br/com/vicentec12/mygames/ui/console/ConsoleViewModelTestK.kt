package br.com.vicentec12.mygames.ui.console

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.model.ConsoleWithGames
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConsoleViewModelTestK {

    @Rule
    @JvmField
    val mInstantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mConsolesLiveDataObserver: Observer<List<ConsoleWithGames>>

    @Mock
    lateinit var mViewFLipperLiveDataObserver: Observer<Int>

    @Mock
    lateinit var mMessageLiveDataObserver: Observer<Int>

    @Mock
    lateinit var mConsoleRepository: ConsoleRepository

    @InjectMocks
    lateinit var mConsoleViewModel: ConsoleViewModel

    lateinit var mConsoles: List<ConsoleWithGames>

    @Before
    fun setup() {
        val mConsole1 = Console(1, "Teste 1", 1)
        val mConsole2 = Console(2, "Teste 2", 2)
        val mGames1 = listOf(Game(1, "Teste 1", "1991", 1),
                Game(2, "Teste 2", "1992", 1))
        val mGames2 = listOf(Game(3, "Teste 3", "1993", 2),
                Game(4, "Teste 4", "1994", 2))
        mConsoles = listOf(ConsoleWithGames(mConsole1, mGames1),
                ConsoleWithGames(mConsole2, mGames2))
    }

    @Test
    fun listConsolesSuccessTest() {
        // Arrange
        val message = R.string.message_consoles_listed
        mConsoleViewModel.consoles.observeForever(mConsolesLiveDataObserver)
        mConsoleViewModel.viewFlipperChild.observeForever(mViewFLipperLiveDataObserver)

        // Act
        mConsoleViewModel.listConsoles()
//        val mResult = verify(mConsoleRepository).listWithGames()
    }

}