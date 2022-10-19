package br.com.vicentec12.mygames.presentation.game

import android.util.SparseBooleanArray
import androidx.core.util.forEach
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.local.entities.GameEntity.Companion.COLUMN_NAME
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.use_case.game.DeleteGamesUseCase
import br.com.vicentec12.mygames.domain.use_case.game.ListGamesUseCase
import br.com.vicentec12.mygames.extensions.error
import br.com.vicentec12.mygames.extensions.sucess
import br.com.vicentec12.mygames.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val mDeleteGamesUseCase: DeleteGamesUseCase,
    private val mListGamesUseCase: ListGamesUseCase
) : ViewModel() {

    // Activity
    private val _orderBy = MutableLiveData<Int>()

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> = _games

    private val _console = MutableLiveData<Console>()
    val console: LiveData<Console> = _console

    private val _viewFlipper = MutableLiveData<Int>()
    val viewFlipper: LiveData<Int> = _viewFlipper

    private val _message = MutableLiveData<Event<Int>>()
    val message: LiveData<Event<Int>> = _message

    private val _pluralMessage = MutableLiveData<Event<Pair<Int, Int>>>()
    val pluralMessage: LiveData<Event<Pair<Int, Int>>> = _pluralMessage

    private val _hasActionModeFinish = MutableLiveData<Event<Boolean>>()
    val hasActionModeFinish: LiveData<Event<Boolean>> = _hasActionModeFinish

    // Adapter
    private val _selectionMode = MutableLiveData(false)
    val selectionMode: LiveData<Boolean> = _selectionMode

    private val _selectedItems = MutableLiveData<SparseBooleanArray>()
    val selectedItems: LiveData<SparseBooleanArray> = _selectedItems

    fun setConsole(mConsole: Console) {
        _console.value = mConsole
    }

    fun listSavedGames() {
        viewModelScope.launch {
            _viewFlipper.value = CHILD_PROGRESS
            mListGamesUseCase(_console.value?.id ?: 0, _orderBy.value ?: COLUMN_NAME).error {
                _viewFlipper.value = CHILD_TEXT
            }.sucess { result ->
                _viewFlipper.value = CHILD_GAMES
                _games.value = result.data.orEmpty()
            }
        }
    }

    fun deleteGames() = viewModelScope.launch {
        val selectedGames = getSelectedGames()
        mDeleteGamesUseCase(selectedGames).error { mResult ->
            _message.value = Event(mResult.message)
        }.sucess { mResult ->
            _games.value?.let { listGames ->
                val newList = ArrayList(listGames)
                newList.removeAll(selectedGames)
                if (newList.isEmpty())
                    _viewFlipper.value = CHILD_TEXT
                _games.value = newList
                _pluralMessage.value = Event(mResult.message to (mResult.data ?: 0))
                _hasActionModeFinish.value = Event(true)
            }
        }
    }

    private fun getSelectedGames(): List<Game> {
        val selectedGames = ArrayList<Game>()
        _selectedItems.value?.forEach { key, _ ->
            _games.value?.get(key)?.let { game -> selectedGames.add(game) }
        }
        return selectedGames
    }

    fun clearSelection() {
        _selectedItems.value?.let { selections ->
            selections.clear()
            _selectedItems.value = selections
        }
    }

    fun setSelectionModeVisible(isSelectionModeVisible: Boolean) {
        _selectionMode.value = isSelectionModeVisible
    }

    fun select(mPosition: Int) {
        if (_selectedItems.value == null)
            _selectedItems.value = SparseBooleanArray()
        _selectedItems.value?.let { selections ->
            if (selections.get(mPosition, false))
                selections.delete(mPosition)
            else
                selections.put(mPosition, true)
            _selectedItems.value = selections
        }
    }

    fun selectAll() {
        _selectedItems.value?.let { selections ->
            val hasAllSelected = _games.value?.size == selections.size()
            selections.clear()
            if (!hasAllSelected) {
                _games.value?.forEachIndexed { index, _ -> selections.put(index, true) }
            }
            _selectedItems.value = selections
        }
    }

    fun getSelectedItemCount() = _selectedItems.value?.size()

    fun isGameSelected(mPosition: Int) = _selectedItems.value?.get(mPosition) ?: false

    fun setOrderBy(orderBy: Int) {
        _orderBy.value = orderBy
    }

    companion object {
        private const val CHILD_PROGRESS = 0
        private const val CHILD_GAMES = 1
        private const val CHILD_TEXT = 2
    }

}