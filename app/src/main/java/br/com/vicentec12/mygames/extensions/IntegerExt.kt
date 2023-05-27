package br.com.vicentec12.mygames.extensions

val Int.Companion.ZERO
    get() = 0

fun Int?.orValue(mValue: Int) = this ?: mValue

fun Int?.orZero() = this.orValue(Int.ZERO)
