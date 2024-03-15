package br.com.vicentec12.mygames.extensions

val Long.Companion.ZERO
    get() = 0L

fun Long?.orValue(mValue: Long) = this ?: mValue

fun Long?.orZero() = this.orValue(Long.ZERO)
