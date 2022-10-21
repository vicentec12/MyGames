package br.com.vicentec12.mygames.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.use_case.console.InsertAllConsolesUseCase
import br.com.vicentec12.mygames.domain.use_case.console.ListWithGamesUseCase
import br.com.vicentec12.mygames.extensions.error
import br.com.vicentec12.mygames.extensions.success
import br.com.vicentec12.mygames.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mListWithGamesUseCase: ListWithGamesUseCase,
    private val mInsertAllConsolesUseCase: InsertAllConsolesUseCase
) : ViewModel() {

    private val _message = MutableLiveData<Event<Int>>()
    val message: LiveData<Event<Int>> = _message

    private val _hasFinish = MutableLiveData<Event<Boolean>>()
    val hasFinish: LiveData<Event<Boolean>> = _hasFinish

    fun loadOrCreateConsoles() {
        viewModelScope.launch {
            mListWithGamesUseCase().error {
                insertConsoles()
            }.success {
                _hasFinish.value = Event(true)
            }
        }
    }

    private fun insertConsoles() = viewModelScope.launch {
        val mConsoles = listOf(
            Console(name = "Nintendo Entertainment System", image = R.drawable.lg_nes),
            Console(name = "Super Nintendo", image = R.drawable.lg_snes),
            Console(name = "Nintendo 64", image = R.drawable.lg_n64),
            Console(name = "Nintendo Gamecube", image = R.drawable.lg_gc),
            Console(name = "Nintendo Wii", image = R.drawable.lg_wii),
            Console(name = "Nintendo 3DS", image = R.drawable.lg_3ds),
            Console(name = "Nintendo DS", image = R.drawable.lg_nds),
            Console(name = "Nintendo Switch", image = R.drawable.lg_ns),
            Console(name = "PlayStation", image = R.drawable.lg_ps1),
            Console(name = "PlayStation 2", image = R.drawable.lg_ps2),
            Console(name = "PlayStation 3", image = R.drawable.lg_ps3),
            Console(name = "PlayStation 4", image = R.drawable.lg_ps4),
            Console(name = "Xbox", image = R.drawable.lg_xbox),
            Console(name = "Xbox 360", image = R.drawable.lg_x360),
            Console(name = "Xbox One", image = R.drawable.lg_xone)
        )
        mInsertAllConsolesUseCase(mConsoles).error { mResult ->
            _message.value = Event(mResult.message)
        }.success {
            _hasFinish.value = Event(true)
        }
    }

}