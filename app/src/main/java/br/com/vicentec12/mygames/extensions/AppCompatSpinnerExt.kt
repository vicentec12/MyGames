package br.com.vicentec12.mygames.extensions

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import br.com.vicentec12.mygames.R

/**
 * Função responsável por validar se a seleção do {@link androidx.appcompat.widget.AppCompatSpinner} é válida
 * e exibir um erro caso necessário.
 *
 * @param errorMessage: Mensagem que será exibida pelo AlertDialog.
 */
fun AppCompatSpinner.validateSelection(errorMessage: String) = if (selectedItemPosition == 0) {
    AlertDialog.Builder(context).setTitle(R.string.title_alert_warning).setMessage(errorMessage)
            .setPositiveButton(R.string.label_alert_button_ok, null).show()
    false
} else true