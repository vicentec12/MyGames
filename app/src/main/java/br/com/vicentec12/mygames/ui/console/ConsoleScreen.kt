package br.com.vicentec12.mygames.ui.console

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.ui.theme.*
import br.com.vicentec12.mygames.util.FunctionEmpty
import br.com.vicentec12.mygames.util.OnItemClickListener

@Composable
fun ConsoleScreen(
    mUiState: ConsoleViewModel.UiState?,
    mOnItemClick: OnItemClickListener<Console>? = null,
    mFabOnClick: FunctionEmpty? = null
) {
    Scaffold(
        backgroundColor = backgroundRecycler,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = primary,
                onClick = { mFabOnClick?.invoke() }
            ) {
                Icon(Icons.Filled.Add, "", tint = Color.White)
            }
        },
        content = { mPadding -> ConsoleContent(mPadding, mUiState, mOnItemClick) }
    )
}

@Composable
fun ConsoleContent(
    mPadding: PaddingValues,
    uiState: ConsoleViewModel.UiState?,
    mOnItemClick: OnItemClickListener<Console>? = null
) {
    when (uiState) {
        is ConsoleViewModel.UiState.Consoles -> ConsolesListed(
            mPadding = mPadding,
            uiState = uiState,
            mOnItemClick = mOnItemClick
        )
        is ConsoleViewModel.UiState.Loading -> ConsolesLoading()
        else -> {}
    }
}

@Composable
fun ConsolesLoading() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = primary)
    }
}

@Composable
fun ConsolesListed(
    mPadding: PaddingValues,
    uiState: ConsoleViewModel.UiState.Consoles,
    mOnItemClick: OnItemClickListener<Console>? = null
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = smallDimen,
            bottom = recyclerBottomWithFabDimen,
            start = smallDimen,
            end = smallDimen
        ),
        verticalArrangement = Arrangement.spacedBy(smallDimen),
        horizontalArrangement = Arrangement.spacedBy(smallDimen)
    ) {
        itemsIndexed(uiState.mConsoles) { mPosition, mConsole ->
            ConsoleItem(mConsole = mConsole, mPosition = mPosition, mOnItemClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsoleContentPreview() {
    val uiState =
        ConsoleViewModel.UiState.Consoles(mockConsoles(), R.string.message_consoles_listed)
    MyGamesTheme {
        ConsoleScreen(uiState)
    }
}

fun mockConsoles() = listOf(
    Console(name = "Nintendo Entertainment System", image = R.drawable.lg_nes),
    Console(name = "Super Nintendo", image = R.drawable.lg_snes),
    Console(name = "Nintendo 64", image = R.drawable.lg_n64),
    Console(name = "Nintendo Gamecube", image = R.drawable.lg_gc)
)
