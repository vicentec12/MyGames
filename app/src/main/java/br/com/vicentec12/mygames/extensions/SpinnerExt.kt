package br.com.vicentec12.mygames.extensions

import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import br.com.vicentec12.mygames.R

/**
 * Função responsável por validar se a seleção do {@link androidx.appcompat.widget.AppCompatSpinner} é válida
 * e exibir um erro caso necessário.
 *
 * @param errorMessage: Mensagem que será exibida pelo AlertDialog.
 */
fun Spinner.validateSelection(errorMessage: String) = if (selectedItemPosition == 0) {
    AlertDialog.Builder(context).setTitle(R.string.title_alert_warning).setMessage(errorMessage)
            .setPositiveButton(R.string.label_alert_button_ok, null).show()
    false
} else true