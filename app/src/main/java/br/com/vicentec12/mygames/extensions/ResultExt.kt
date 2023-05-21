package br.com.vicentec12.mygames.extensions

import br.com.vicentec12.mygames.data.Result

/**
 * @author Vicente de Castro
 */

/**
 * Return error if this happens.
 * @param function body with error param
 * @return same object
 */
fun <R> Result<R>.error(body: (Result.Error) -> Unit): Result<R> {
    if (this is Result.Error)
        body(this)
    return this
}

/**
 * Return success if this happens.
 * @param function body with success param and data
 */
fun <R> Result<R>.success(body: (Result.Success<R>) -> Unit) {
    if (this is Result.Success)
        body(this)
}

/**
 * Return @{Result.Success} mapped object.
 * @param body: map function body
 * @return Result.Success mapped
 */
fun <R, T> Result.Success<R>.map(body: (R?) -> T): Result.Success<T> {
    return Result.Success(body(this.data), this.message)
}

/**
 * Return @{Result} mapped object.
 * @param body: map function body
 * @return Result mapped
 */
@Suppress("UNCHECKED_CAST")
fun <R, T> Result<R>.map(body: (R?) -> T): Result<T> {
    return when (this) {
        is Result.Success -> this.map(body)
        else -> this as Result<T>
    }
}
