package br.com.vicentec12.mygames.presentation.console

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.use_case.console.ListWithGamesUseCase
import br.com.vicentec12.mygames.extensions.error
import br.com.vicentec12.mygames.extensions.sucess
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

    private val _consoles = MutableLiveData<List<Console>>()
    val consoles: LiveData<List<Console>> = _consoles

    fun listConsoles() {
        viewModelScope.launch {
            _viewFlipperChild.value = CHILD_PROGRESS
            mListWithGamesUseCase().error { mResult ->
                _message.value = mResult.message
                _viewFlipperChild.value = CHILD_MESSAGE
            }.sucess { result ->
                _consoles.value = result.data.orEmpty()
                _viewFlipperChild.value = CHILD_CONSOLES
            }
        }
    }

    companion object {

        private const val CHILD_PROGRESS = 0
        private const val CHILD_CONSOLES = 1
        private const val CHILD_MESSAGE = 2

    }

}