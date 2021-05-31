package br.com.vicentec12.mygames.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val TAG_LOG = "DateUtil"

    fun getCurrentDateBr(): String? {
        return getDateTime("dd/MM/yyyy")
    }

    fun getCurrentDate(): String? {
        return getDateTime("yyyy-MM-dd")
    }

    fun getCurrentDateTimeBr(): String? {
        return getDateTime("dd/MM/yyyy HH:mm:ss")
    }

    fun getCurrentDateTime(): String? {
        return getDateTime("yyyy-MM-dd HH:mm:ss")
    }

    fun getDateTime(formato: String?): String? {
        val sdf = SimpleDateFormat(formato, Locale.getDefault())
        return sdf.format(Date())
    }

    fun getAllDaysOfMonth(year: Int, month: Int): List<String>? {
        val calendar = Calendar.getInstance()
        calendar[year, month - 1] = 1
        val qtdDias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val days: MutableList<String> = ArrayList()
        for (i in 1..qtdDias) {
            days.add(String.format(Locale.getDefault(), "%02d", i))
        }
        return days
    }

    fun getAllMonths(): List<String>? {
        val months: MutableList<String> = ArrayList()
        for (i in 0..11) {
            months.add(String.format(Locale.getDefault(), "%02d", i))
        }
        return months
    }

    fun getYears(): List<String>? {
        val years: MutableList<String> = ArrayList()
        val c = Calendar.getInstance(TimeZone.getDefault())
        val currentYear = c[Calendar.YEAR]
        years.add((currentYear - 1).toString())
        years.add(currentYear.toString())
        years.add((currentYear + 1).toString())
        return years
    }

    fun getLongYears(): List<String>? {
        val currentYear = Calendar.getInstance(TimeZone.getDefault())[Calendar.YEAR]
        val anos: MutableList<String> = ArrayList()
        for (i in 0..16) {
            anos.add((currentYear + i).toString())
        }
        return anos
    }

    fun validateDate(date: String?): Boolean {
        if (date == null || !date.contains("/")) return false
        try {
            val df: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            df.isLenient = false
            val calendar = Calendar.getInstance()
            calendar.time = df.parse(date)
            return calendar[Calendar.YEAR] >= 1900
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun validateTime(time: String?): Boolean {
        var time = time
        if (time == null || !time.contains(":")) return false
        try {
            if (time.length < 8) time += ":00"
            val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            df.isLenient = false
            df.parse(time)
            return true
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun isHourInInterval(inicio: String, fim: String): Boolean {
        val date = Calendar.getInstance()
        val dateInicio = convertTimeToCalendar(inicio)
        val dateFim = convertTimeToCalendar(fim)
        return date.after(dateInicio) && date.before(dateFim)
    }

    fun convertHourToMinute(s: String): Int {
        val hourMin = s.split(":").toTypedArray()
        if (hourMin.size > 1) {
            val hour = hourMin[0].toInt()
            val mins = hourMin[1].toInt()
            return hour * 60 + mins
        }
        return 0
    }

    fun convertMinuteToTime(minutosInt: Int): String? {
        val horas = minutosInt / 60
        val minutos = minutosInt % 60
        return String.format(Locale.getDefault(), "%02d:%02d", horas, minutos)
    }

    fun converMinuteToTimeComplete(segundosInt: Long): String? {
        val horas = segundosInt / 3600
        val minutos = segundosInt % 3600 / 60
        val segundos = segundosInt % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segundos)
    }

    fun convertMinuteToSecond(minuto: Int): Int {
        return minuto * 60
    }

    fun getBrDate(date: String): Date? {
        var date = date
        try {
            if (date.trim { it <= ' ' }.length == 16) date += ":00"
            return SimpleDateFormat(if (date.trim { it <= ' ' }.length > 10) "dd/MM/yyyy HH:mm:ss" else "dd/MM/yyyy", Locale.getDefault()).parse(date)
        } catch (e: ParseException) {
            Log.e(TAG_LOG, e.message!!)
        }
        return null
    }

    fun getDate(date: String): Date? {
        var date = date
        try {
            if (date.trim { it <= ' ' }.length == 16) date += ":00"
            return SimpleDateFormat(if (date.trim { it <= ' ' }.length > 10) "yyyy-MM-dd HH:mm:ss" else "yyyy-MM-dd", Locale.getDefault()).parse(date)
        } catch (e: ParseException) {
            Log.e(TAG_LOG, e.message!!)
        }
        return null
    }

    fun getTime(time: String): Date? {
        var time = time
        try {
            if (time.length < 8) time += ":00"
            time = String.format("%s %s", getCurrentDateBr(), time)
            return SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).parse(time)
        } catch (e: ParseException) {
            Log.e(TAG_LOG, e.message!!)
        }
        return null
    }

    fun convertDateUsPattern(data: String): String? {
        var data = data
        val date = getBrDate(data)
        return if (date != null) {
            if (data.length == 16) data += ":00"
            SimpleDateFormat(if (data.length > 10) "yyyy-MM-dd HH:mm:ss" else "yyyy-MM-dd", Locale.getDefault()).format(date)
        } else data
    }

    fun convertDateBrPattern(data: String): String? {
        var data = data
        val date = getDate(data)
        return if (date != null) {
            if (data.length == 16) data += ":00"
            SimpleDateFormat(if (data.length > 10) "dd/MM/yyyy HH:mm:ss" else "dd/MM/yyyy", Locale.getDefault()).format(date)
        } else data
    }

    fun convertBrDateToCalendar(dateString: String): Calendar? {
        val calendar = Calendar.getInstance()
        if (validateDate(dateString)) {
            val date = getBrDate(dateString)
            if (date != null) calendar.time = date
        }
        return calendar
    }

    fun convertTimeToCalendar(timeString: String): Calendar {
        val date = getTime(timeString)
        val calendar = Calendar.getInstance()
        if (date != null) calendar.time = date
        return calendar
    }

    fun setupDateInTextInputLayout(context: Context?, _tilText: TextInputLayout) {
        _tilText.setEndIconOnClickListener {
            val calendar: Calendar? = convertBrDateToCalendar(_tilText.editText!!.text.toString())
            val datePickerDialog = DatePickerDialog(context!!, { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                _tilText.editText!!.setText("") // Para não dar erro na máscara, precisa desse código
                _tilText.editText!!.setText(String.format(Locale.getDefault(), "%02d%02d%d", dayOfMonth, monthOfYear + 1, year))
            }, calendar?.get(Calendar.YEAR)!!, calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
            datePickerDialog.show()
        }
    }

    fun setupTimeInTextInputLayout(context: Context?, _tilText: TextInputLayout) {
        _tilText.setEndIconOnClickListener {
            val calendar: Calendar = convertTimeToCalendar(_tilText.editText!!.text.toString())
            val datePickerDialog = TimePickerDialog(context, { view: TimePicker?, hourOfDay: Int, minute: Int ->
                _tilText.editText!!.setText("") // Para não dar erro na máscara, precisa desse código
                _tilText.editText!!.setText(String.format(Locale.getDefault(), "%02d%02d", hourOfDay, minute))
            }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true)
            datePickerDialog.show()
        }
    }


}