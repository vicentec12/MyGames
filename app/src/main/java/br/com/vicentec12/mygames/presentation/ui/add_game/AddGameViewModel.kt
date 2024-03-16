package br.com.vicentec12.mygames.presentation.ui.add_game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.commons.extensions.EMPTY
import br.com.vicentec12.mygames.commons.extensions.error
import br.com.vicentec12.mygames.commons.extensions.isYear
import br.com.vicentec12.mygames.commons.extensions.success
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.use_case.game.InsertGameUseCase
import br.com.vicentec12.mygames.domain.use_case.game.UpdateGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGameViewModel @Inject constructor(
    private val mInsertGameUseCase: InsertGameUseCase,
    private val mUpdateGameUseCase: UpdateGameUseCase
) : ViewModel() {

    private val _gameName = mutableStateOf(String.EMPTY)
    val gameName: State<String>
        get() = _gameName

    private val _gameYear = mutableStateOf(String.EMPTY)
    val gameYear: State<String>
        get() = _gameYear

    private val _successInsert = MutableStateFlow<Boolean?>(false)
    val successInsert = _successInsert.asStateFlow()

    private val _message = MutableStateFlow<Int?>(null)
    val message = _message.asStateFlow()

    private val _nameFieldError = MutableStateFlow<Int?>(null)
    val nameFieldError = _nameFieldError.asStateFlow()

    private val _yearFieldError = MutableStateFlow<Int?>(null)
    val yearFieldError = _yearFieldError.asStateFlow()

    private fun insertGame(mGame: Game) {
        viewModelScope.launch {
            mInsertGameUseCase(mGame).error { mResult ->
                _message.value = mResult.message
            }.success { mResult ->
                _message.value = mResult.message
                _successInsert.value = true
            }
        }
    }

    private fun updateGame(mGame: Game) {
        viewModelScope.launch {
            mUpdateGameUseCase(mGame).error { mResult ->
                _message.value = mResult.message
            }.success { mResult ->
                _message.value = mResult.message
            }
        }
    }

    fun saveOrUpdateGame(
        mGame: Game?,
        mConsoleId: Long
    ) {
        removeFieldsError()
        if (validateFields()) {
            val mCopyGame = mGame?.copy(
                name = gameName.value,
                year = gameYear.value,
            ) ?: Game(
                name = gameName.value,
                year = gameYear.value,
                consoleId = mConsoleId
            )
            if (mCopyGame.id > 0) updateGame(mCopyGame) else insertGame(mCopyGame)
        }
    }

    fun setGameName(name: String) {
        _gameName.value = name
    }

    fun setGameYear(year: String) {
        _gameYear.value = year
    }

    fun initGameNameYear(game: Game?) {
        if (_gameName.value.isEmpty() && _gameYear.value.isEmpty()) {
            setGameName(game?.name.orEmpty())
            setGameYear(game?.year.orEmpty())
        }
    }

    fun cleanSuccessInsert() {
        _successInsert.value = false
    }

    fun messageShown() {
        _message.value = null
    }

    private fun validateFields() = if (gameName.value.isEmpty()) {
        _nameFieldError.value = R.string.error_message_empty_field
        false
    } else if (gameYear.value.isEmpty()) {
        _yearFieldError.value = R.string.error_message_empty_field
        false
    } else if (gameYear.value.isYear().not()) {
        _yearFieldError.value = R.string.error_message_invalid_year
        false
    } else {
        true
    }

    private fun removeFieldsError() {
        _nameFieldError.value = null
        _yearFieldError.value = null
    }
}