package br.com.vicentec12.mygames.presentation.console

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import br.com.vicentec12.mygames.domain.use_case.console.ListWithGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsoleViewModel @Inject constructor(
    private val mListWithGamesUseCase: ListWithGamesUseCase
) : ViewModel() {

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    private val _viewFlipperChild = MutableLiveData<Int>()
    val viewFlipperChild: LiveData<Int> = _viewFlipperChild

    private val _consoles = MutableLiveData<ArrayList<Console>>()
    val consoles: LiveData<ArrayList<Console>> = _consoles

    fun listConsoles() = viewModelScope.launch {
        _viewFlipperChild.value = CHILD_PROGRESS
        when (val result = mListWithGamesUseCase()) {
            is Result.Success -> {
                _consoles.value = result.data ?: arrayListOf()
                _viewFlipperChild.value = CHILD_CONSOLES
            }
            is Result.Error -> {
                _message.value = result.message
                _viewFlipperChild.value = CHILD_MESSAGE
            }
        }
    }

    companion object {

        private const val CHILD_PROGRESS = 0
        private const val CHILD_CONSOLES = 1
        private const val CHILD_MESSAGE = 2

    }

}