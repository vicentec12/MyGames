package br.com.vicentec12.mygames.ui.game

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.ui.commons.ErrorMessageScreen
import br.com.vicentec12.mygames.ui.commons.LoadingScreen
import br.com.vicentec12.mygames.ui.commons.MyGamesTopAppBar
import br.com.vicentec12.mygames.ui.commons.UiState
import br.com.vicentec12.mygames.ui.theme.MyGamesTheme
import br.com.vicentec12.mygames.ui.theme.backgroundRecycler
import br.com.vicentec12.mygames.ui.theme.dimen4x
import br.com.vicentec12.mygames.util.FunctionEmpty
import br.com.vicentec12.mygames.util.FunctionReturn
import br.com.vicentec12.mygames.util.OnItemClickListener
import br.com.vicentec12.mygames.util.OnItemLongClickListener

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GameScreen(
    navController: NavController,
    uiState: UiState<List<Game>>?,
    isSelectionMode: FunctionReturn<Boolean>,
    selectedItems: FunctionReturn<SparseBooleanArray?>,
    onItemClickListener: OnItemClickListener<Game>? = null,
    onItemLongClickListener: OnItemLongClickListener<Game>? = null,
    onItemOrderByClick: FunctionEmpty = { }
) {
    Scaffold(
        topBar = {
            GameTopBar(
                navController = navController,
                onItemOrderByClick = onItemOrderByClick
            )
        },
        backgroundColor = backgroundRecycler,
        content = {
            GameContent(
                uiState = uiState,
                isSelectionMode = isSelectionMode,
                selectedItems = selectedItems,
                onItemClickListener = onItemClickListener,
                onItemLongClickListener = onItemLongClickListener
            )
        }
    )
}

@Composable
fun GameTopBar(
    navController: NavController,
    onItemOrderByClick: FunctionEmpty = { }
) {
    MyGamesTopAppBar(
        appBarTitle = { "Jogos" },
        isShownAppBarNavigationIcon = { true },
        navController = navController,
        actions = {
            IconButton(onClick = onItemOrderByClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_sort),
                    contentDescription = LocalContext.current.getString(R.string.content_description_order_by)
                )
            }
        }
    )
}

@Composable
fun GameContent(
    uiState: UiState<List<Game>>?,
    isSelectionMode: FunctionReturn<Boolean>,
    selectedItems: FunctionReturn<SparseBooleanArray?>,
    onItemClickListener: OnItemClickListener<Game>? = null,
    onItemLongClickListener: OnItemLongClickListener<Game>? = null
) {
    uiState?.also {
        when (it) {
            is UiState.Loading -> LoadingScreen()
            is UiState.Success -> GameList(
                games = it.data,
                isSelectionMode = isSelectionMode,
                selectedItems = selectedItems,
                onItemClickListener = onItemClickListener,
                onItemLongClickListener = onItemLongClickListener
            )
            is UiState.Error -> ErrorMessageScreen(message = it.message)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameList(
    games: List<Game>,
    isSelectionMode: FunctionReturn<Boolean>,
    selectedItems: FunctionReturn<SparseBooleanArray?>,
    onItemClickListener: OnItemClickListener<Game>? = null,
    onItemLongClickListener: OnItemLongClickListener<Game>? = null
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
            GameItem(
                game = game,
                index = index,
                modifier = Modifier.animateItemPlacement(),
                isSelectionMode = isSelectionMode,
                selectedItems = selectedItems,
                onItemClickListener = onItemClickListener,
                onItemLongClickListener = onItemLongClickListener
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GameScreenPreview() {
    MyGamesTheme {
        GameContent(
            uiState = UiState.Success(
                listOf(
                    Game(1, "The Legend of Zelda", "1986", 1),
                    Game(2, "The Legend of Zelda 2", "1988", 1)
                )
            ),
            isSelectionMode = { false },
            selectedItems = { null }
        )
    }
}