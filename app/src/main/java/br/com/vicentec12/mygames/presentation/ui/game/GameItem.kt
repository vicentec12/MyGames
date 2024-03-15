package br.com.vicentec12.mygames.presentation.ui.game

import android.util.SparseBooleanArray
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.orFalse
import br.com.vicentec12.mygames.presentation.theme.*
import br.com.vicentec12.mygames.util.FunctionReturn
import br.com.vicentec12.mygames.util.OnItemClickListener
import br.com.vicentec12.mygames.util.OnItemLongClickListener

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameItem(
    game: Game,
    index: Int,
    modifier: Modifier = Modifier,
    isSelectionMode: FunctionReturn<Boolean>,
    selectedItems: FunctionReturn<SparseBooleanArray?>,
    onItemClickListener: OnItemClickListener<Game>? = null,
    onItemLongClickListener: OnItemLongClickListener<Game>? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = { onItemClickListener?.invoke(game, index) },
                onLongClick = { onItemLongClickListener?.invoke(game, index) }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(dimen2x)
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .padding(dimen8x)
        ) {
            val (name, year, check) = createRefs()
            Text(
                text = game.name,
                style = TitleText,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(check.start)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = game.year,
                style = SubtitleText,
                modifier = Modifier.constrainAs(year) {
                    top.linkTo(name.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(check.start)
                    width = Dimension.fillToConstraints
                }
            )
            this@Card.AnimatedVisibility(
                visible = isSelectionMode(),
                modifier = Modifier.constrainAs(check) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Checkbox(
                    checked = selectedItems()?.get(index).orFalse(),
                    onCheckedChange = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemPreview() {
    MyGamesTheme {
        GameItem(
            game = Game(1, "The Legend of Zelda", "1986", 1),
            index = 1,
            modifier = Modifier,
            { false },
            { null }
        )
    }
}