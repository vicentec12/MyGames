package br.com.vicentec12.mygames.presentation.ui.console

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.R.string
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.presentation.commons.LoadingScreen
import br.com.vicentec12.mygames.presentation.commons.MyGamesTopAppBar
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.presentation.theme.backgroundRecycler
import br.com.vicentec12.mygames.presentation.theme.dimen4x
import br.com.vicentec12.mygames.presentation.ui.console.ConsoleViewModel.UiState
import br.com.vicentec12.mygames.presentation.ui.console.ConsoleViewModel.UiState.Consoles
import br.com.vicentec12.mygames.presentation.ui.console.ConsoleViewModel.UiState.Loading
import br.com.vicentec12.mygames.util.OnItemClickListener

@Composable
fun ConsoleScreen(
    uiState: UiState?,
    onItemClick: OnItemClickListener<Console>? = null,
) {
    Scaffold(
        topBar = { ConsoleTopBar() },
        containerColor = backgroundRecycler,
    ) { innerPadding ->
        ConsoleContent(innerPadding, uiState, onItemClick)
    }
}

@Composable
fun ConsoleTopBar() {
    MyGamesTopAppBar(
        mAppBarTitle = { LocalContext.current.getString(string.app_name) }
    )
}

@Composable
fun ConsoleContent(
    paddingValues: PaddingValues,
    uiState: UiState?,
    onItemClick: OnItemClickListener<Console>? = null
) {
    when (uiState) {
        is Consoles -> ConsolesListed(
            paddingValues = paddingValues,
            uiState = uiState,
            onItemClick = onItemClick
        )

        is Loading -> LoadingScreen(paddingValues)
        else -> {}
    }
}

@Composable
fun ConsolesListed(
    paddingValues: PaddingValues,
    uiState: Consoles,
    onItemClick: OnItemClickListener<Console>? = null
) {
    val mConsoles = remember { mutableStateOf(uiState.mConsoles) }
    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimen4x),
        verticalArrangement = Arrangement.spacedBy(dimen4x),
        horizontalArrangement = Arrangement.spacedBy(dimen4x)
    ) {
        itemsIndexed(
            items = mConsoles.value,
            key = { _, item -> item.id }
        ) { mPosition, mConsole ->
            ConsoleItem(
                mConsole = mConsole,
                mPosition = mPosition,
                onItemClickListener = onItemClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsoleContentPreview() {
    val uiState =
        Consoles(mockConsoles(), string.message_consoles_listed)
    MyGamesTheme {
        ConsoleScreen(uiState)
    }
}

fun mockConsoles() = listOf(
    Console(id = 1, name = "Nintendo Entertainment System", image = R.drawable.lg_nes),
    Console(id = 2, name = "Super Nintendo", image = R.drawable.lg_snes),
    Console(id = 3, name = "Nintendo 64", image = R.drawable.lg_n64),
    Console(id = 4, name = "Nintendo Gamecube", image = R.drawable.lg_gc)
)
