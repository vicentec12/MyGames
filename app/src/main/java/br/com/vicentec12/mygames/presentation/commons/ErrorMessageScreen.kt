package br.com.vicentec12.mygames.presentation.commons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.presentation.theme.ErrorText
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.presentation.theme.dimen8x

@Composable
fun ErrorMessageScreen(
    paddingValues: PaddingValues,
    @StringRes message: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(dimen8x),
            style = ErrorText,
            text = LocalContext.current.getString(message)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMessagePreview() {
    MyGamesTheme {
        ErrorMessageScreen(
            paddingValues = PaddingValues(dimen8x),
            message = R.string.error_message_empty_field
        )
    }
}
