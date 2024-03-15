package br.com.vicentec12.mygames.presentation.ui.console

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.use_case.console.ListWithGamesUseCase
import br.com.vicentec12.mygames.extensions.error
import br.com.vicentec12.mygames.extensions.success
import br.com.vicentec12.mygames.presentation.ui.console.ConsoleViewModel.UiState.Consoles
import br.com.vicentec12.mygames.presentation.ui.console.ConsoleViewModel.UiState.Error
import br.com.vicentec12.mygames.presentation.ui.console.ConsoleViewModel.UiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsoleViewModel @Inject constructor(
    private val mListWithGamesUseCase: ListWithGamesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(Loading)
    val uiState: LiveData<UiState> = _uiState

    fun listConsoles() {
        viewModelScope.launch {
            _uiState.value = Loading
            mListWithGamesUseCase().error { mResult ->
                _uiState.value = Error(mResult.message)
            }.success { result ->
                _uiState.value = Consoles(result.data.orEmpty(), result.message)
            }
        }
    }

    companion object {

        private const val CHILD_PROGRESS = 0
        private const val CHILD_CONSOLES = 1
        private const val CHILD_MESSAGE = 2

    }

    sealed class UiState {
        object Loading : UiState()
        class Consoles(val mConsoles: List<Console>, @StringRes val mMessage: Int) : UiState()
        class Error(@StringRes val mMessage: Int) : UiState()
    }

}