package br.com.vicentec12.mygames.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private static final String TAG_LOG = "DateUtil";

    public static String getCurrentDateBr() {
        return getDateTime("dd/MM/yyyy");
    }

    public static String getCurrentDate() {
        return getDateTime("yyyy-MM-dd");
    }

    public static String getCurrentDateTimeBr() {
        return getDateTime("dd/MM/yyyy HH:mm:ss");
    }

    public static String getCurrentDateTime() {
        return getDateTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateTime(String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
        return sdf.format(new Date());
    }

    public static List<String> getAllDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, (month - 1), 1);
        int qtdDias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<String> days = new ArrayList<>();
        for (int i = 1; i <= qtdDias; i++) {
            days.add(String.format(Locale.getDefault(), "%02d", i));
        }
        return days;
    }

    public static List<String> getAllMonths() {
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            months.add(String.format(Locale.getDefault(), "%02d", i));
        }
        return months;
    }

    public static List<String> getYears() {
        List<String> years = new ArrayList<>();
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = c.get(Calendar.YEAR);
        years.add(String.valueOf(currentYear - 1));
        years.add(String.valueOf(currentYear));
        years.add(String.valueOf(currentYear + 1));
        return years;
    }

    public static List<String> getLongYears() {
        int currentYear = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR);
        List<String> anos = new ArrayList<>();
        for (int i = 0; i <= 16; i++) {
            anos.add(String.valueOf((currentYear + i)));
        }
        return anos;
    }

    public static boolean validateDate(String date) {
        if (date == null || !date.contains("/")) return false;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            df.setLenient(false);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(df.parse(date));
            return calendar.get(Calendar.YEAR) >= 1900;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validateTime(String time) {
        if (time == null || !time.contains(":")) return false;
        try {
            if (time.length() < 8) time += ":00";
            DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            df.setLenient(false);
            df.parse(time);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isHourInInterval(String inicio, String fim) {
        Calendar date = Calendar.getInstance();
        Calendar dateInicio = convertTimeToCalendar(inicio);
        Calendar dateFim = convertTimeToCalendar(fim);
        return (date.after(dateInicio) && date.before(dateFim));
    }

    public static int convertHourToMinute(String s) {
        String[] hourMin = s.split(":");
        if (hourMin.length > 1) {
            int hour = Integer.parseInt(hourMin[0]);
            int mins = Integer.parseInt(hourMin[1]);
            return (hour * 60) + mins;
        }
        return 0;
    }

    public static String convertMinuteToTime(int minutosInt) {
        int horas = minutosInt / 60;
        int minutos = minutosInt % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", horas, minutos);
    }

    public static String converMinuteToTimeComplete(long segundosInt) {
        long horas = segundosInt / 3600;
        long minutos = (segundosInt % 3600) / 60;
        long segundos = segundosInt % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segundos);
    }

    public static int convertMinuteToSecond(int minuto) {
        return minuto * 60;
    }

    public static Date getBrDate(String date) {
        try {
            if (date.trim().length() == 16) date += ":00";
            return new SimpleDateFormat(date.trim().length() > 10 ? "dd/MM/yyyy HH:mm:ss" : "dd/MM/yyyy", Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            Log.e(TAG_LOG, e.getMessage());
        }
        return null;
    }

    public static Date getDate(String date) {
        try {
            if (date.trim().length() == 16) date += ":00";
            return new SimpleDateFormat(date.trim().length() > 10 ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd", Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            Log.e(TAG_LOG, e.getMessage());
        }
        return null;
    }

    public static Date getTime(String time) {
        try {
            if (time.length() < 8) time += ":00";
            time = String.format("%s %s", getCurrentDateBr(), time);
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).parse(time);
        } catch (ParseException e) {
            Log.e(TAG_LOG, e.getMessage());
        }
        return null;
    }

    public static String convertDateUsPattern(String data) {
        Date date = getBrDate(data);
        if (date != null) {
            if (data.length() == 16) data += ":00";
            return new SimpleDateFormat(data.length() > 10 ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd", Locale.getDefault()).format(date);
        } else
            return data;
    }

    public static String convertDateBrPattern(String data) {
        Date date = getDate(data);
        if (date != null) {
            if (data.length() == 16) data += ":00";
            return new SimpleDateFormat(data.length() > 10 ? "dd/MM/yyyy HH:mm:ss" : "dd/MM/yyyy", Locale.getDefault()).format(date);
        } else
            return data;
    }

    public static Calendar convertBrDateToCalendar(String dateString) {
        Calendar calendar = Calendar.getInstance();
        if (validateDate(dateString)) {
            Date date = getBrDate(dateString);
            if (date != null)
                calendar.setTime(date);
        }
        return calendar;
    }

    public static Calendar convertTimeToCalendar(String timeString) {
        Date date = getTime(timeString);
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar;
    }

    public static void setupDateInTextInputLayout(Context context, TextInputLayout _tilText) {
        _tilText.setEndIconOnClickListener(v -> {
            Calendar calendar = DateUtil.convertBrDateToCalendar(_tilText.getEditText().getText().toString());
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
                _tilText.getEditText().setText(""); // Para não dar erro na máscara, precisa desse código
                _tilText.getEditText().setText(String.format(Locale.getDefault(), "%02d%02d%d", dayOfMonth, (monthOfYear + 1), year));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }

    public static void setupTimeInTextInputLayout(Context context, TextInputLayout _tilText) {
        _tilText.setEndIconOnClickListener(v -> {
            Calendar calendar = DateUtil.convertTimeToCalendar(_tilText.getEditText().getText().toString());
            TimePickerDialog datePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                _tilText.getEditText().setText(""); // Para não dar erro na máscara, precisa desse código
                _tilText.getEditText().setText(String.format(Locale.getDefault(), "%02d%02d", hourOfDay, minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            datePickerDialog.show();
        });
    }

}
