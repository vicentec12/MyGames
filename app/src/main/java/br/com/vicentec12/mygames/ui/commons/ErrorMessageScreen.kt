package br.com.vicentec12.mygames.ui.commons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.ui.theme.ErrorText
import br.com.vicentec12.mygames.ui.theme.MyGamesTheme
import br.com.vicentec12.mygames.ui.theme.dimen8x

@Composable
fun ErrorMessageScreen(
    @StringRes message: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimen8x),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            style = ErrorText,
            text = LocalContext.current.getString(message)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMessagePreview() {
    MyGamesTheme {
        ErrorMessageScreen(R.string.error_message_empty_field)
    }
}
