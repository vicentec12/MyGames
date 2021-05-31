package br.com.vicentec12.mygames.ui.game

import android.util.SparseBooleanArray
import androidx.core.util.forEach
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.data.model.Game.Companion.COLUMN_NAME
import br.com.vicentec12.mygames.data.source.Result
import br.com.vicentec12.mygames.data.source.game.GameDataSource
import br.com.vicentec12.mygames.util.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameViewModel @Inject constructor(
        private val mRepository: GameDataSource
) : ViewModel() {

    // Activity
    private val _games = MutableLiveData<List<Game?>>()
    val games: LiveData<List<Game?>>
        get() = _games

    private val _console = MutableLiveData<Console>()
    val console: LiveData<Console>
        get() = _console

    private val _orderBy = MutableLiveData<Int>()

    private val _viewFlipper = MutableLiveData<Int>()
    val viewFlipper: LiveData<Int>
        get() = _viewFlipper

    private val _message = MutableLiveData<Event<Int>>()
    val message: LiveData<Event<Int>>
        get() = _message

    private val _pluralMessage = MutableLiveData<Event<List<Int>>>()
    val pluralMessage: LiveData<Event<List<Int>>>
        get() = _pluralMessage

    private val _hasActionModeFinish = MutableLiveData<Event<Boolean>>()
    val hasActionModeFinish: LiveData<Event<Boolean>>
        get() = _hasActionModeFinish

    // Adapter
    private val _selectionMode = MutableLiveData(false)
    val selectionMode: LiveData<Boolean>
        get() = _selectionMode

    private val _selectedItems = MutableLiveData<SparseBooleanArray>()
    val selectedItems: LiveData<SparseBooleanArray>
        get() = _selectedItems

    fun setConsole(mConsole: Console) {
        _console.value = mConsole
    }

    fun listSavedGames() = viewModelScope.launch {
        _viewFlipper.value = CHILD_PROGRESS
        when (val result = mRepository.list(_console.value?.id ?: 0,
                _orderBy.value ?: COLUMN_NAME)) {
            is Result.Success -> {
                _viewFlipper.value = CHILD_GAMES
                _games.value = result.data ?: ArrayList()
            }
            is Result.Error -> {
                _viewFlipper.value = CHILD_TEXT
            }
        }
    }

    fun deleteGames() = viewModelScope.launch {
        val selectedGames = getSelectedGames()
        when (val result = mRepository.delete(selectedGames)) {
            is Result.Success -> {
                _games.value?.let { listGames ->
                    val newList = listGames.toMutableList()
                    newList.removeAll(selectedGames)
                    if (newList.isEmpty())
                        _viewFlipper.value = CHILD_TEXT
                    _games.value = newList
                    _pluralMessage.value = Event(createPluralMessage(result.message, result.data!!))
                    _hasActionModeFinish.value = Event(true)
                }
            }
            is Result.Error -> {
                _message.value = Event(result.message)
            }
        }
    }

    private fun createPluralMessage(mMessage: Int, mQuantity: Int) =
            listOf(mMessage, mQuantity)

    private fun getSelectedGames(): List<Game> {
        val selectedGames = ArrayList<Game>()
        _selectedItems.value?.forEach { key, _ ->
            _games.value?.get(key)?.let { game ->
                selectedGames.add(game)
            }
        }
        return selectedGames
    }

    fun clearSelection() {
        _selectedItems.value?.let { selections ->
            selections.clear()
            _selectedItems.value = selections
        }
    }

    fun showChecks(isSelectionModeVisible: Boolean) {
        _selectionMode.value = isSelectionModeVisible
    }

    fun select(mPosition: Int) {
        if (_selectedItems.value == null) _selectedItems.value = SparseBooleanArray()
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
            val hasAllSelected = _games.value?.size != selections.size()
            selections.clear()
            if (hasAllSelected) {
                _games.value?.forEachIndexed { index, _ ->
                    selections.put(index, true)
                }
            }
            _selectedItems.value = selections
        }
    }

    fun getSelectedItemCount() = _selectedItems.value?.size()

    fun isSelectionModeVisible() = _selectionMode.value

    fun isGameSelected(mPosition: Int) = _selectedItems.value?.get(mPosition, false) ?: false

    fun setOrderBy(orderBy: Int) {
        _orderBy.value = orderBy
    }

    companion object {
        private const val CHILD_PROGRESS = 0
        private const val CHILD_GAMES = 1
        private const val CHILD_TEXT = 2
    }

}