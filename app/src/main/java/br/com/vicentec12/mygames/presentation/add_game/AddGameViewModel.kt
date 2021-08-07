package br.com.vicentec12.mygames.presentation.add_game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.use_case.console.ListConsolesUseCase
import br.com.vicentec12.mygames.domain.use_case.game.InsertGameUseCase
import br.com.vicentec12.mygames.domain.use_case.game.UpdateGameUseCase
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

    private val _consoles = MutableLiveData<ArrayList<Console>>()
    val consoles: LiveData<ArrayList<Console>> = _consoles

    private val _message = MutableLiveData<Event<Int>>()
    val message: LiveData<Event<Int>> = _message

    private val _success = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>> = _success

    private fun insertGame(mGame: Game) = viewModelScope.launch {
        when (val mResult = mInsertGameUseCase(mGame)) {
            is Result.Success -> {
                _message.value = Event(mResult.message)
                _success.value = Event(true)
                _game.value = Game()
            }
            is Result.Error -> _message.value = Event(mResult.message)
        }
    }

    private fun updateGame(mGame: Game) = viewModelScope.launch {
        when (val mResult = mUpdateGameUseCase(mGame)) {
            is Result.Success -> {
                _message.value = Event(mResult.message)
                _success.value = Event(true)
            }
            is Result.Error -> _message.value = Event(mResult.message)
        }
    }

    fun listConsoles(mGame: Game?) = viewModelScope.launch {
        if (_consoles.value == null) {
            when (val mResult = mListConsolesUseCase()) {
                is Result.Success -> {
                    _consoles.value = mResult.data ?: arrayListOf()
                    _game.postValue(mGame ?: Game())
                }
                is Result.Error -> _message.value = Event(mResult.message)
            }
        }
    }

    fun databaseEvent() {
        _game.value?.run { if (id > 0) updateGame(this) else insertGame(this) }
    }

}