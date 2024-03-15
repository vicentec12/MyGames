package br.com.vicentec12.mygames.presentation.console

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.local.entities.ConsoleEntity
import br.com.vicentec12.mygames.data.local.entities.ConsoleWithGamesEntity
import br.com.vicentec12.mygames.data.local.entities.GameEntity
import br.com.vicentec12.mygames.data.repository.ConsoleRepositoryImpl
import br.com.vicentec12.mygames.presentation.ui.console.ConsoleViewModel
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
    lateinit var mConsolesLiveDataObserver: Observer<List<ConsoleWithGamesEntity>>

    @Mock
    lateinit var mViewFLipperLiveDataObserver: Observer<Int>

    @Mock
    lateinit var mMessageLiveDataObserver: Observer<Int>

    @Mock
    lateinit var mConsoleRepository: ConsoleRepositoryImpl

    @InjectMocks
    lateinit var mConsoleViewModel: ConsoleViewModel

    lateinit var mConsoles: List<ConsoleWithGamesEntity>

    @Before
    fun setup() {
        val mConsole1 = ConsoleEntity(1, "Teste 1", 1)
        val mConsole2 = ConsoleEntity(2, "Teste 2", 2)
        val mGames1 = listOf(
            GameEntity(1, "Teste 1", "1991", 1),
                GameEntity(2, "Teste 2", "1992", 1)
        )
        val mGames2 = listOf(
            GameEntity(3, "Teste 3", "1993", 2),
                GameEntity(4, "Teste 4", "1994", 2)
        )
        mConsoles = listOf(
            ConsoleWithGamesEntity(mConsole1, mGames1),
                ConsoleWithGamesEntity(mConsole2, mGames2)
        )
    }

    @Test
    fun listConsolesSuccessTest() {
        // Arrange
        val message = R.string.message_consoles_listed
//        mConsoleViewModel.consoles.observeForever(mConsolesLiveDataObserver)
//        mConsoleViewModel.viewFlipperChild.observeForever(mViewFLipperLiveDataObserver)

        // Act
        mConsoleViewModel.listConsoles()
//        val mResult = verify(mConsoleRepository).listWithGames()
    }

}