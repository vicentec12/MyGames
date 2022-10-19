package br.com.vicentec12.mygames.extensions

import br.com.vicentec12.mygames.data.Result

fun <R> Result<R>.error(body: (Result.Error) -> Unit): Result<R> {
    if (this is Result.Error)
        body(this)
    return this
}

fun <R> Result<R>.sucess(body: (Result.Success<R>) -> Unit) {
    if (this is Result.Success)
        body(this)
}