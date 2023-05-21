package br.com.vicentec12.mygames.ui.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.ui.theme.*
import br.com.vicentec12.mygames.util.OnItemClickListener

@Composable
fun GameItem(
    game: Game,
    index: Int,
    mOnItemClickListener: OnItemClickListener<Game>? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
                mOnItemClickListener?.invoke(game, index)
            },
        shape = RoundedCornerShape(dimen2x)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(dimen8x)
        ) {
            Text(
                text = game.name,
                style = TitleText
            )
            Text(
                text = game.year,
                style = SubtitleText
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemPreview() {
    MyGamesTheme {
        GameItem(game = Game(1, "The Legend of Zelda", "1986", 1), 1)
    }
}