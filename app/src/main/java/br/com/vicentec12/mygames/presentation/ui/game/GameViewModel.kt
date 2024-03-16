package br.com.vicentec12.mygames.presentation.ui.game

import android.util.SparseBooleanArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.commons.extensions.error
import br.com.vicentec12.mygames.commons.extensions.orFalse
import br.com.vicentec12.mygames.commons.extensions.orValue
import br.com.vicentec12.mygames.commons.extensions.orZero
import br.com.vicentec12.mygames.commons.extensions.success
import br.com.vicentec12.mygames.data.local.entities.GameEntity.Companion.COLUMN_NAME
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.use_case.game.DeleteGamesUseCase
import br.com.vicentec12.mygames.domain.use_case.game.ListGamesUseCase
import br.com.vicentec12.mygames.presentation.commons.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val mDeleteGamesUseCase: DeleteGamesUseCase,
    private val mListGamesUseCase: ListGamesUseCase
) : ViewModel() {

    // Activity

    private val _uiState = MutableStateFlow<UiState<List<Game>>?>(null)
    val uiState = _uiState.asStateFlow()

    private val _orderBy = MutableLiveData<Int>()

    private val _message = MutableStateFlow<Int?>(null)
    val message = _message.asStateFlow()

    private val _pluralMessage = MutableStateFlow<Pair<Int, Int>?>(null)
    val pluralMessage = _pluralMessage.asStateFlow()

    // Adapter
    private val _selectionMode = MutableStateFlow(false)
    val selectionMode = _selectionMode.asStateFlow()

    private val _selectedItems = MutableStateFlow<SparseBooleanArray?>(null)
    val selectedItems = _selectedItems.asStateFlow()

    fun listSavedGames(mConsole: Console?) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            mListGamesUseCase(mConsole?.id.orZero(), _orderBy.value.orValue(COLUMN_NAME)).error {
                _uiState.value = UiState.Error(R.string.message_games_empty)
            }.success { result ->
                _uiState.value = UiState.Success(result.data.orEmpty())
            }
        }
    }

    fun deleteGames() = viewModelScope.launch {
        val selectedGames = getSelectedGames()
        mDeleteGamesUseCase(selectedGames).error { mResult ->
            _message.value = mResult.message
        }.success { mResult ->
            getGameList().also { listGames ->
                val newList = ArrayList(listGames)
                newList.removeAll(selectedGames.toSet())
                if (newList.isEmpty()) {
                    _uiState.value = UiState.Error(R.string.message_games_empty)
                } else {
                    _uiState.value = UiState.Success(newList)
                }
                _pluralMessage.value = mResult.message to mResult.data.orZero()
                finishSelectionMode()
            }
        }
    }

    private fun getSelectedGames() = getGameList().filterIndexed { index, _ ->
        _selectedItems.value?.get(index).orFalse()
    }

    fun initSelectionMode(mPosition: Int) {
        _selectionMode.value = true
        select(mPosition)
    }

    fun finishSelectionMode() {
        _selectionMode.value = false
        _selectedItems.value = null
    }

    fun select(mPosition: Int) {
        if (_selectedItems.value == null)
            _selectedItems.value = SparseBooleanArray()
        _selectedItems.value = _selectedItems.value?.clone()?.apply {
            if (get(mPosition, false))
                delete(mPosition)
            else
                put(mPosition, true)
        }
    }

    fun selectAll() {
        _selectedItems.value = _selectedItems.value?.clone()?.apply {
            val hasAllSelected = getGameList().size == size()
            clear()
            if (!hasAllSelected) {
                getGameList().forEachIndexed { index, _ -> put(index, true) }
            }
        }
    }

    fun getSelectedItemCount() = selectedItems.value?.size().orZero()

    fun isGameSelected(mPosition: Int) = selectedItems.value?.get(mPosition).orFalse()

    fun setOrderBy(orderBy: Int) {
        _orderBy.value = orderBy
    }

    fun messageShown() {
        _message.value = null
    }

    fun pluralMessageShown() {
        _pluralMessage.value = null
    }

    private fun getGameList() = (_uiState.value as? UiState.Success)?.data.orEmpty()

    companion object {
        private const val CHILD_PROGRESS = 0
        private const val CHILD_GAMES = 1
        private const val CHILD_TEXT = 2
    }

}