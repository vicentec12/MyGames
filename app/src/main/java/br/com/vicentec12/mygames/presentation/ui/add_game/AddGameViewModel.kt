package br.com.vicentec12.mygames.presentation.ui.add_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.use_case.console.ListConsolesUseCase
import br.com.vicentec12.mygames.domain.use_case.game.InsertGameUseCase
import br.com.vicentec12.mygames.domain.use_case.game.UpdateGameUseCase
import br.com.vicentec12.mygames.extensions.error
import br.com.vicentec12.mygames.extensions.isYear
import br.com.vicentec12.mygames.extensions.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGameViewModel @Inject constructor(
    private val mListConsolesUseCase: ListConsolesUseCase,
    private val mInsertGameUseCase: InsertGameUseCase,
    private val mUpdateGameUseCase: UpdateGameUseCase
) : ViewModel() {

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
        mName: String,
        mYear: String,
        mConsoleId: Long
    ) {
        removeFieldsError()
        if (validateFields(mName, mYear)) {
            val mCopyGame = mGame?.copy(
                name = mName,
                year = mYear,
            ) ?: Game(
                name = mName,
                year = mYear,
                consoleId = mConsoleId
            )
            if (mCopyGame.id > 0) updateGame(mCopyGame) else insertGame(mCopyGame)
        }
    }

    private fun validateFields(
        mName: String,
        mYear: String,
    ) = if (mName.isEmpty()) {
        _nameFieldError.value = R.string.error_message_empty_field
        false
    } else if (mYear.isEmpty()) {
        _yearFieldError.value = R.string.error_message_empty_field
        false
    } else if (mYear.isYear().not()) {
        _yearFieldError.value = R.string.error_message_invalid_year
        false
    } else {
        true
    }

    private fun removeFieldsError() {
        _nameFieldError.value = null
        _yearFieldError.value = null
    }

    fun successInsert() {
        _successInsert.value = false
    }

    fun messageShown() {
        _message.value = null
    }
}