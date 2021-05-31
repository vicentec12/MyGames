package br.com.vicentec12.mygames.data.source

sealed class Result<out R> {

    class Success<out T>(val data: T? = null, val message: Int = 0) : Result<T>()

    class Error(val message: Int) : Result<Nothing>()

}