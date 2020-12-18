package br.com.vicentec12.mygames.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.textfield.TextInputLayout;

import br.com.vicentec12.mygames.R;

public class ValidationUtil {

    public static boolean validateEmptyField(Context context, TextInputLayout _tilText) {
        if (_tilText == null) return false;
        if (_tilText.getEditText().getText().toString().trim().isEmpty()) {
            createError(_tilText, String.format(context.getString(R.string.error_message_empty_field), _tilText.getHint()));
            return false;
        }
        return true;
    }

    public static boolean validateDateField(Context context, TextInputLayout _tilText) {
        if (_tilText == null) return false;
        if (!DateUtil.validateDate(_tilText.getEditText().getText().toString())) {
            createError(_tilText, context.getString(R.string.error_message_invalid_date));
            return false;
        }
        return true;
    }

    public static boolean validateTimeField(Context context, TextInputLayout _tilText) {
        if (_tilText == null) return false;
        if (!DateUtil.validateTime(_tilText.getEditText().getText().toString())) {
            createError(_tilText, context.getString(R.string.error_message_invalid_time));
            return false;
        }
        return true;
    }

    public static boolean validateFloatField(Context context, TextInputLayout _tilText) {
        if (_tilText == null) return false;
        try {
            String valor = _tilText.getEditText().getText().toString().replace(",", ".");
            float valorConvertido = Float.parseFloat(valor);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            createError(_tilText, context.getString(R.string.error_message_invalid_value));
            return false;
        }
        return true;
    }

    public static boolean validateNumCharacters(Context context, TextInputLayout _tilText, int quantity) {
        if (_tilText == null) return false;
        if (_tilText.getEditText().getText().toString().trim().length() < quantity) {
            createError(_tilText, context.getString(R.string.error_message_invalid_num_characters,
                    _tilText.getHint(), quantity));
            return false;
        }
        return true;
    }

    public static void createError(TextInputLayout _tilText, String message) {
        _tilText.setError(message);
        _tilText.getParent().requestChildFocus(_tilText, _tilText);
        if (_tilText.getEndIconMode() != TextInputLayout.END_ICON_NONE)
            _tilText.setErrorIconDrawable(null);
    }

    public static void removeErrorTextInputLayout(TextInputLayout _tilText) {
        if (_tilText == null) return;
        if (_tilText.getError() != null) {
            _tilText.setError(null);
            _tilText.setErrorEnabled(false);
        }
    }

    public static boolean validateSpinners(Context context, AppCompatSpinner _spSpinner, String msgError) {
        if (_spSpinner == null) return false;
        if (_spSpinner.getSelectedItemPosition() == 0) {
            new AlertDialog.Builder(context).setTitle(R.string.title_alert_warning).setMessage(msgError)
                    .setPositiveButton(R.string.label_alert_button_ok, null).show();
            return false;
        }
        return true;
    }

}
