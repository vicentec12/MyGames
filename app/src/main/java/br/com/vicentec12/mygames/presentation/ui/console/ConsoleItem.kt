package br.com.vicentec12.mygames.presentation.ui.console

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.R.drawable
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.commons.extensions.orZero
import br.com.vicentec12.mygames.presentation.theme.*
import br.com.vicentec12.mygames.commons.util.OnItemClickListener

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConsoleItem(
    mConsole: Console,
    mPosition: Int,
    onItemClickListener: OnItemClickListener<Console>? = null
) {
    val gamesCount = mConsole.games?.size.orZero()
    Card(
        shape = RoundedCornerShape(dimen4x),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = true)
        ) {
            onItemClickListener?.invoke(mConsole, mPosition)
        }
    ) {
        Column(
            modifier = Modifier.padding(dimen8x),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = mConsole.image),
                contentDescription = LocalContext.current.getString(R.string.content_description_console_image),
                modifier = Modifier.size(itemConsoleItemHeightDimen)
            )
            Text(
                text = mConsole.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimen2x),
                textAlign = TextAlign.Center,
                style = TitleText
            )
            Text(
                text = pluralStringResource(R.plurals.plural_games, gamesCount, gamesCount),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = SubtitleText
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsoleItemPreview() {
    MyGamesTheme {
        ConsoleItem(
            mConsole = Console(
                1,
                name = "Nintendo Entertainment System",
                image = drawable.lg_nes,
                games = arrayListOf()
            ),
            1
        )
    }
}