package br.com.vicentec12.mygames.presentation.ui.game

import android.util.SparseBooleanArray
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.vicentec12.mygames.R.drawable
import br.com.vicentec12.mygames.R.string
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.commons.extensions.EMPTY
import br.com.vicentec12.mygames.presentation.commons.ErrorMessageScreen
import br.com.vicentec12.mygames.presentation.commons.LoadingScreen
import br.com.vicentec12.mygames.presentation.components.MyGamesTopAppBar
import br.com.vicentec12.mygames.presentation.commons.UiState
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.presentation.theme.backgroundRecycler
import br.com.vicentec12.mygames.presentation.theme.dimen4x
import br.com.vicentec12.mygames.presentation.theme.recyclerBottomWithFabDimen
import br.com.vicentec12.mygames.commons.util.FunctionEmpty
import br.com.vicentec12.mygames.commons.util.FunctionReturn
import br.com.vicentec12.mygames.commons.util.OnItemClickListener
import br.com.vicentec12.mygames.commons.util.OnItemLongClickListener
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    navController: NavController? = null,
    uiState: UiState<List<Game>>?,
    isSelectionMode: FunctionReturn<Boolean>,
    selectedItems: FunctionReturn<SparseBooleanArray?>,
    onItemClickListener: OnItemClickListener<Game>? = null,
    onItemLongClickListener: OnItemLongClickListener<Game>? = null,
    onItemOrderByClick: FunctionEmpty = { },
    onClickFab: FunctionEmpty? = null,
    showSnackbar: FunctionReturn<String?> = { null }
) {
    val scope = rememberCoroutineScope()
    val mSnackbarHostState = remember { SnackbarHostState() }
    val message = showSnackbar()
    println("Mensagem: $message")
    if (message.orEmpty().isNotEmpty()) {
        println("Mensagem: $message")
        LaunchedEffect(mSnackbarHostState) {
            scope.launch { mSnackbarHostState.showSnackbar(message.orEmpty()) }
        }
    }
    Scaffold(
        topBar = {
            GameTopBar(
                mNavController = navController,
                mOnItemOrderByClick = onItemOrderByClick
            )
        },
        containerColor = backgroundRecycler,
        snackbarHost = { SnackbarHost(hostState = mSnackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onClickFab?.invoke() }
            ) {
                Icon(Icons.Filled.Add, String.EMPTY, tint = Color.White)
            }
        }
    ) { innerPadding ->
        GameContent(
            paddingValues = innerPadding,
            uiState = uiState,
            isSelectionMode = isSelectionMode,
            selectedItems = selectedItems,
            onItemClickListener = onItemClickListener,
            onItemLongClickListener = onItemLongClickListener
        )
    }
}

@Composable
fun GameTopBar(
    mNavController: NavController? = null,
    mOnItemOrderByClick: FunctionEmpty = { }
) {
    MyGamesTopAppBar(
        mAppBarTitle = { LocalContext.current.getString(string.text_my_games) },
        mNavController = mNavController,
        mActions = {
            IconButton(onClick = mOnItemOrderByClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = drawable.ic_sort),
                    contentDescription = LocalContext.current.getString(string.content_description_order_by)
                )
            }
        }
    )
}

@Composable
fun GameContent(
    paddingValues: PaddingValues,
    uiState: UiState<List<Game>>?,
    isSelectionMode: FunctionReturn<Boolean>,
    selectedItems: FunctionReturn<SparseBooleanArray?>,
    onItemClickListener: OnItemClickListener<Game>? = null,
    onItemLongClickListener: OnItemLongClickListener<Game>? = null
) {
    uiState?.also {
        when (it) {
            is UiState.Loading -> LoadingScreen(paddingValues)
            is UiState.Success -> GameList(
                paddingValues = paddingValues,
                games = it.data,
                isSelectionMode = isSelectionMode,
                selectedItems = selectedItems,
                onItemClickListener = onItemClickListener,
                onItemLongClickListener = onItemLongClickListener
            )

            is UiState.Error -> ErrorMessageScreen(
                paddingValues = paddingValues,
                message = it.message
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameList(
    paddingValues: PaddingValues,
    games: List<Game>,
    isSelectionMode: FunctionReturn<Boolean>,
    selectedItems: FunctionReturn<SparseBooleanArray?>,
    onItemClickListener: OnItemClickListener<Game>? = null,
    onItemLongClickListener: OnItemLongClickListener<Game>? = null
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = dimen4x,
            bottom = recyclerBottomWithFabDimen,
            start = dimen4x,
            end = dimen4x
        ),
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
        GameScreen(
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