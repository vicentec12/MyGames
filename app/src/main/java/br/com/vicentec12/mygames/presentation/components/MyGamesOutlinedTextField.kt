package br.com.vicentec12.mygames.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.vicentec12.mygames.extensions.EMPTY
import br.com.vicentec12.mygames.extensions.orFalse

@Composable
fun MyGamesOutlinedTextField(
    modifier: Modifier = Modifier,
    value: MutableState<String>? = null,
    label: String? = null,
    error: String? = null,
    maxLength: Int? = null,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    OutlinedTextField(
        modifier = modifier,
        value = value?.value.orEmpty(),
        onValueChange = {
            if (maxLength == null || it.length <= maxLength) {
                value?.value = it
            }
        },
        isError = error?.isNotEmpty().orFalse(),
        trailingIcon = if (error?.isNotEmpty().orFalse()) {
            {
                Icon(
                    Filled.Info,
                    String.EMPTY,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        } else {
            null
        },
        supportingText = if (error?.isNotEmpty().orFalse()) {
            {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error.orEmpty(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            null
        },
        label = { Text(text = label.orEmpty()) },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions
    )
}

@Preview
@Composable
fun MyGamesOutlinedTextFieldPreview() {
    MyGamesOutlinedTextField(
        value = mutableStateOf("test"),
        label = "Test",
        error = "Error"
    )
}