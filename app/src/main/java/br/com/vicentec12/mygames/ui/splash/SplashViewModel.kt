package br.com.vicentec12.mygames.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.source.Result
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource
import br.com.vicentec12.mygames.util.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
        private val mConsoleRepository: ConsoleDataSource
) : ViewModel() {

    private val _message = MutableLiveData<Event<Int>>()
    val message: LiveData<Event<Int>>
        get() = _message

    private val _hasFinish = MutableLiveData<Event<Boolean>>()
    val hasFinish: LiveData<Event<Boolean>>
        get() = _hasFinish

    fun loadOrCreateConsoles() = viewModelScope.launch {
        when (mConsoleRepository.listWithGames()) {
            is Result.Success -> _hasFinish.value = Event(true)
            is Result.Error -> insertConsoles()
        }
    }

    private fun insertConsoles() = viewModelScope.launch {
        val mConsoles = listOf(
                Console(name = "Nintendo", image = R.drawable.lg_nes),
                Console(name = "Super Nintendo", image = R.drawable.lg_snes),
                Console(name = "Nintendo 64", image = R.drawable.lg_n64),
                Console(name = "Nintendo Gamecube", image = R.drawable.lg_gc),
                Console(name = "Nintendo Wii", image = R.drawable.lg_wii),
                Console(name = "Nintendo 3DS", image = R.drawable.lg_3ds),
                Console(name = "Nintendo DS", image = R.drawable.lg_nds),
                Console(name = "Nintendo Switch", image = R.drawable.lg_switch),
                Console(name = "Playstation", image = R.drawable.lg_ps1),
                Console(name = "Playstation 2", image = R.drawable.lg_ps2),
                Console(name = "Playstation 3", image = R.drawable.lg_ps3),
                Console(name = "Playstation 4", image = R.drawable.lg_ps4),
                Console(name = "Xbox", image = R.drawable.lg_xbox),
                Console(name = "Xbox 360", image = R.drawable.lg_x360),
                Console(name = "Xbox One", image = R.drawable.lg_xone)
        )
        when (val result = mConsoleRepository.insert(mConsoles)) {
            is Result.Success -> _hasFinish.value = Event(true)
            is Result.Error -> _message.value = Event(result.message)
        }
    }

}