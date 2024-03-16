package br.com.vicentec12.mygames.commons.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val String.Companion.EMPTY: String
    get() = ""

fun String.isYear() = try {
    val formatter = SimpleDateFormat(YEAR_PATTERN, Locale.getDefault())
    formatter.parse(this)?.let { date ->
        val cal = Calendar.getInstance()
        cal.time = date
        cal.get(Calendar.YEAR) >= YEAR_MIN
    }.orFalse()
} catch (e: ParseException) {
    false
}

private const val YEAR_PATTERN = "yyyy"
private const val YEAR_MIN = 1900