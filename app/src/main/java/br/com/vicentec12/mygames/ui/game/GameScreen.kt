package br.com.vicentec12.mygames.ui.game

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.ui.commons.ErrorMessage
import br.com.vicentec12.mygames.ui.commons.Loading
import br.com.vicentec12.mygames.ui.commons.UiState
import br.com.vicentec12.mygames.ui.theme.MyGamesTheme
import br.com.vicentec12.mygames.ui.theme.backgroundRecycler
import br.com.vicentec12.mygames.ui.theme.dimen4x
import br.com.vicentec12.mygames.util.OnItemClickListener

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GameScreen(
    mUiState: UiState<List<Game>>?,
    mOnItemClickListener: OnItemClickListener<Game>? = null
) {
    Scaffold(
        backgroundColor = backgroundRecycler,
        content = { GameContent(mUiState = mUiState, mOnItemClickListener) }
    )
}

@Composable
fun GameContent(
    mUiState: UiState<List<Game>>?,
    mOnItemClickListener: OnItemClickListener<Game>? = null
) {
    mUiState?.also {
        when (it) {
            is UiState.Loading -> Loading()
            is UiState.Success -> GameList(it.data, mOnItemClickListener)
            is UiState.Error -> ErrorMessage(message = it.message)
        }
    }
}

@Composable
fun GameList(
    games: List<Game>,
    mOnItemClickListener: OnItemClickListener<Game>? = null
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimen4x),
        verticalArrangement = Arrangement.spacedBy(dimen4x)
    ) {
        itemsIndexed(
            items = games,
            key = { _, game -> game.id }
        ) { index, game ->
            GameItem(game = game, index = index, mOnItemClickListener = mOnItemClickListener)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GameScreenPreview() {
    MyGamesTheme {
        GameContent(mUiState = UiState.Success(listOf(
            Game(1, "The Legend of Zelda", "1986", 1),
            Game(2, "The Legend of Zelda 2", "1988", 1)
        )))
    }
}