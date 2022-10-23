package br.com.vicentec12.mygames.ui.add_game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.use_case.console.ListConsolesUseCase
import br.com.vicentec12.mygames.domain.use_case.game.InsertGameUseCase
import br.com.vicentec12.mygames.domain.use_case.game.UpdateGameUseCase
import br.com.vicentec12.mygames.extensions.error
import br.com.vicentec12.mygames.extensions.success
import br.com.vicentec12.mygames.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGameViewModel @Inject constructor(
    private val mListConsolesUseCase: ListConsolesUseCase,
    private val mInsertGameUseCase: InsertGameUseCase,
    private val mUpdateGameUseCase: UpdateGameUseCase
) : ViewModel() {

    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    private val _consoles = MutableLiveData<List<Console>>()
    val consoles: LiveData<List<Console>> = _consoles

    private val _message = MutableLiveData<Event<Int>>()
    val message: LiveData<Event<Int>> = _message

    private val _success = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>> = _success

    private fun insertGame(mGame: Game) {
        viewModelScope.launch {
            mInsertGameUseCase(mGame).error { mResult ->
                _message.value = Event(mResult.message)
            }.success { mResult ->
                _message.value = Event(mResult.message)
                _success.value = Event(true)
                _game.value = Game()
            }
        }
    }

    private fun updateGame(mGame: Game) {
        viewModelScope.launch {
            mUpdateGameUseCase(mGame).error { mResult ->
                _message.value = Event(mResult.message)
            }.success { mResult ->
                _message.value = Event(mResult.message)
                _success.value = Event(true)
            }
        }
    }

    fun listConsoles() {
        viewModelScope.launch {
            if (_consoles.value == null) {
                mListConsolesUseCase().error { mResult ->
                    _message.value = Event(mResult.message)
                }.success { mResult ->
                    _consoles.value = mResult.data.orEmpty()
                }
            }
        }
    }

    fun databaseEvent() {
        _game.value?.run { if (id > 0) updateGame(this) else insertGame(this) }
    }

    fun setGame(mGame: Game) {
        _game.postValue(mGame)
    }

}