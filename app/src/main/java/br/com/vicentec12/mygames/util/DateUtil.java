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

    public static String getDataAtualBr() {
        return getDataHora("dd/MM/yyyy");
    }

    public static String getDataAtualUs() {
        return getDataHora("yyyy-MM-dd");
    }

    public static String getDataHoraAtualBr() {
        return getDataHora("dd/MM/yyyy HH:mm:ss");
    }

    public static String getDataHoraAtualUs() {
        return getDataHora("yyyy-MM-dd HH:mm:ss");
    }

    public static String getDataHora(String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String convertStringToDate(String dataHora) throws ParseException {
        SimpleDateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        SimpleDateFormat formatAfter = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
        Date date = formatBefore.parse(dataHora);
        return formatAfter.format(date);
    }

    public static List<String> getTodosDiasDoMes(int ano, int mes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, (mes - 1), 1);
        int qtdDias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<String> dias = new ArrayList<>();
        for (int i = 1; i <= qtdDias; i++) {
            dias.add(String.valueOf(i));
        }
        return dias;
    }

    public static String[] getTodosMeses() {
        return new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
                , "11", "12"};
    }

    public static String[] getAno() {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int anoAtual = c.get(Calendar.YEAR);
        return new String[]{(anoAtual - 1) + "", anoAtual + ""};
    }

    public static List<String> getHorarioComercial() {
        List<String> horarioComercial = new ArrayList<>();
        for (int i = 7; i < 23; i++) {
            horarioComercial.add(i + "");
        }
        return horarioComercial;
    }

    public static boolean validateDate(String date) {
        if (date == null) return false;
        if (!date.contains("/")) return false;
        if (!date.trim().isEmpty()) {
            try {
                String[] arrayDate = date.split("/");
                if (arrayDate.length < 3) return false;
                if (Integer.parseInt(arrayDate[2]) < 1900) return false;
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                df.setLenient(false);
                df.parse(date);
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean validateTime(String time) {
        if (time == null) return false;
        if (!time.contains(":")) return false;
        if (!time.trim().isEmpty()) {
            try {
                if (time.length() < 8) time += ":00";
                String[] arrayTime = time.split(":");
                if (arrayTime.length < 3) return false;
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                df.setLenient(false);
                df.parse(time);
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Date getHourMinuteFromString(String hourMinute) {
        String[] hora = hourMinute.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hora[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(hora[1]));
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static boolean isHourInInterval(String inicio, String fim) {
        Date date = Calendar.getInstance().getTime();
        Date dateInicio = getHourMinuteFromString(inicio);
        Date dateFim = getHourMinuteFromString(fim);
        return (date.after(dateInicio) && date.before(dateFim));
    }

    public static Calendar converterStringBrDateParaCalendar(String data) {
        if (validateDate(data)) {
            String[] arrayDate = data.split("/");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(arrayDate[2]), Integer.parseInt(arrayDate[1]) - 1, Integer.parseInt(arrayDate[0]));
            return calendar;
        } else
            return Calendar.getInstance();
    }

    public static int converterHoraParaMinutos(String s) {
        String[] hourMin = s.split(":");
        if (hourMin.length > 1) {
            int hour = Integer.parseInt(hourMin[0]);
            int mins = Integer.parseInt(hourMin[1]);
            return (hour * 60) + mins;
        }
        return 0;
    }

    public static String converterMinutosParaHoraMinutos(int minutosInt) {
        int horas = minutosInt / 60;
        int minutos = minutosInt % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", horas, minutos);
    }

    public static String converterMinutosParaHoraMinutosSegundos(long segundosInt) {
        long horas = segundosInt / 3600;
        long minutos = (segundosInt % 3600) / 60;
        long segundos = segundosInt % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segundos);
    }

    public static int converterMinutoParaSegundo(int minuto) {
        return minuto * 60;
    }

    public static Date recuperarDataBr(String data) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(data);
        } catch (ParseException e) {
            Log.e(TAG_LOG, e.getMessage());
        }
        return null;
    }

    public static Date recuperarDataUs(String data) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(data);
        } catch (ParseException e) {
            Log.e(TAG_LOG, e.getMessage());
        }
        return null;
    }

    public static Date recuperarDataHoraBr(String data) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).parse(data);
        } catch (ParseException e) {
            Log.e(TAG_LOG, e.getMessage());
        }
        return null;
    }

    public static Date recuperarDataHoraUs(String data) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(data);
        } catch (ParseException e) {
            Log.e(TAG_LOG, e.getMessage());
        }
        return null;
    }

    public static String converterDataPadraoUs(String data) {
        Date date = recuperarDataBr(data);
        if (date != null)
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
        else
            return data;
    }

    public static String converterDataPadraoBr(String data) {
        Date date = recuperarDataUs(data);
        if (date != null)
            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
        else
            return data;
    }

    public static String converterDataHoraPadraoBr(String data) {
        Date date = recuperarDataHoraUs(data);
        if (date != null) {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(date);
        } else
            return data;
    }

    public static String converterDataHoraPadraoUs(String data) {
        Date date = recuperarDataHoraBr(data);
        if (date != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
        } else
            return data;
    }

    public static void configurarDatePickerInTextInputLayout(Context context, TextInputLayout _tilText) {
        _tilText.setEndIconOnClickListener(v -> {
            Calendar calendar = DateUtil.converterStringBrDateParaCalendar(_tilText.getEditText().getText().toString());
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
                _tilText.getEditText().setText(""); // Para não dar erro na máscara, precisa desse código
                _tilText.getEditText().setText(String.format(Locale.getDefault(), "%02d%02d%d", dayOfMonth, (monthOfYear + 1), year));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
//            datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setAllCaps(false);
//            datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setAllCaps(false);
        });
    }

    public static void configurarTimePickerInTextInputLayout(Context context, TextInputLayout _tilText) {
        _tilText.setEndIconOnClickListener(v -> {
            Calendar calendar = DateUtil.converterStringBrDateParaCalendar(_tilText.getEditText().getText().toString());
            TimePickerDialog datePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                _tilText.getEditText().setText(""); // Para não dar erro na máscara, precisa desse código
                _tilText.getEditText().setText(String.format(Locale.getDefault(), "%02d%02d", hourOfDay, minute));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            datePickerDialog.show();
//            datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setAllCaps(false);
//            datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setAllCaps(false);
        });
    }

}
