package br.com.vicentec12.mygames.ui.commons

sealed class UiState<out T> {
    class Success<out R>(val data: R) : UiState<R>()
    class Error(val message: Int) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}