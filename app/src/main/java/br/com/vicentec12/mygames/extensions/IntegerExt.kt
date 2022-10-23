package br.com.vicentec12.mygames.extensions

val Int.Companion.ZERO
    get() = 0

fun Int?.orZero() = this ?: Int.ZERO