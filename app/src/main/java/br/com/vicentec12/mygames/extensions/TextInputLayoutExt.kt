package br.com.vicentec12.mygames.extensions

import br.com.vicentec12.mygames.R
import com.google.android.material.textfield.TextInputLayout

const val YEAR_QUANTITY = 4

/**
 * Função responsável por validar se o campo está vazio e exibir um erro caso necessário.
 */
fun TextInputLayout.validateEmptyField() = if (editText?.text.toString().trim().isEmpty()) {
    createError(context.getString(R.string.error_message_empty_field, hint))
    false
} else true

/**
 * Função responsável por validar a quantidade de caracteres existente e exibir um erro caso necessário.
 *
 * @param quantity: Quantidade mínima permitida pelo campo de texto.
 */
fun TextInputLayout.validateQtdCharacters(quantity: Int) =
    if (editText?.text.toString().trim().length < quantity) {
        createError(
            context.getString(
                R.string.error_message_invalid_num_characters,
                hint,
                quantity
            )
        )
        false
    } else true

/**
 * Função responsável por mostrar um erro no campo de texto e exigir o foco para ele.
 *
 * @param message: Mensagem que será exibida no erro.
 */
fun TextInputLayout.createError(message: String) {
    error = message
    parent.requestChildFocus(this, this)
    if (endIconMode != TextInputLayout.END_ICON_NONE)
        errorIconDrawable = null
}

/**
 * Função responsável por remover erro do campo de texto.
 */
fun TextInputLayout.removeError() {
    error?.let {
        error = null
        isErrorEnabled = false
    }
}

