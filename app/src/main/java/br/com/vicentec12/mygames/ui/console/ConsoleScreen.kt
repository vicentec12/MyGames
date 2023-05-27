package br.com.vicentec12.mygames.ui.console

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.ui.commons.LoadingScreen
import br.com.vicentec12.mygames.ui.commons.MyGamesTopAppBar
import br.com.vicentec12.mygames.ui.theme.*
import br.com.vicentec12.mygames.util.FunctionEmpty
import br.com.vicentec12.mygames.util.OnItemClickListener

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConsoleScreen(
    mUiState: ConsoleViewModel.UiState?,
    mOnItemClick: OnItemClickListener<Console>? = null,
    mFabOnClick: FunctionEmpty? = null
) {
    Scaffold(
        topBar = { ConsoleTopBar() },
        backgroundColor = backgroundRecycler,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = primary,
                onClick = { mFabOnClick?.invoke() }
            ) {
                Icon(Icons.Filled.Add, "", tint = Color.White)
            }
        },
        content = { ConsoleContent(mUiState, mOnItemClick) }
    )
}

@Composable
fun ConsoleTopBar( ) {
    MyGamesTopAppBar(
        appBarTitle = { LocalContext.current.getString(R.string.app_name) }
    )
}

@Composable
fun ConsoleContent(
    uiState: ConsoleViewModel.UiState?,
    mOnItemClick: OnItemClickListener<Console>? = null
) {
    when (uiState) {
        is ConsoleViewModel.UiState.Consoles -> ConsolesListed(
            uiState = uiState,
            mOnItemClick = mOnItemClick
        )
        is ConsoleViewModel.UiState.Loading -> LoadingScreen()
        else -> {}
    }
}

@Composable
fun ConsolesListed(
    uiState: ConsoleViewModel.UiState.Consoles,
    mOnItemClick: OnItemClickListener<Console>? = null
) {
    val mConsoles = remember { mutableStateOf(uiState.mConsoles) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = dimen4x,
            bottom = recyclerBottomWithFabDimen,
            start = dimen4x,
            end = dimen4x
        ),
        verticalArrangement = Arrangement.spacedBy(dimen4x),
        horizontalArrangement = Arrangement.spacedBy(dimen4x)
    ) {
        itemsIndexed(
            items = mConsoles.value,
            key = { _, item -> item.id }
        ) { mPosition, mConsole ->
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
    Console(id = 1, name = "Nintendo Entertainment System", image = R.drawable.lg_nes),
    Console(id = 2, name = "Super Nintendo", image = R.drawable.lg_snes),
    Console(id = 3, name = "Nintendo 64", image = R.drawable.lg_n64),
    Console(id = 4, name = "Nintendo Gamecube", image = R.drawable.lg_gc)
)
