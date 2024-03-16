package br.com.vicentec12.mygames.commons.util

/**
 * Responsável por usar o valor apenas uma vez e limpar a variável LiveData.
 */
class Event<T>(private val mContent: T?) {

    private var hasBeenHandled = false

    val contentIfNotHandled: T?
        get() = if (!hasBeenHandled) {
            hasBeenHandled = true
            mContent
        } else null

}