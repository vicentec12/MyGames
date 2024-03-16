package br.com.vicentec12.mygames.presentation.ui.add_game

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import br.com.vicentec12.mygames.R.string
import br.com.vicentec12.mygames.commons.YEAR_LENGTH
import br.com.vicentec12.mygames.commons.extensions.EMPTY
import br.com.vicentec12.mygames.commons.extensions.orFalse
import br.com.vicentec12.mygames.commons.extensions.orZero
import br.com.vicentec12.mygames.commons.util.FunctionEmpty
import br.com.vicentec12.mygames.commons.util.FunctionParam
import br.com.vicentec12.mygames.commons.util.FunctionReturn
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.presentation.components.MyGamesOutlinedTextField
import br.com.vicentec12.mygames.presentation.components.MyGamesTopAppBar
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.presentation.theme.dimen4x
import br.com.vicentec12.mygames.presentation.theme.dimen8x
import br.com.vicentec12.mygames.presentation.ui.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AddGameScreen(
    navController: NavController? = null,
    viewModel: AddGameViewModel,
    activityViewModel: MainViewModel
) {
    viewModel.initGameNameYear(activityViewModel.selectedGame)
    val context: Context = LocalContext.current
    val successInsert = viewModel.successInsert.collectAsStateWithLifecycle()
    val message = viewModel.message.collectAsStateWithLifecycle()
    val nameFieldError = viewModel.nameFieldError.collectAsStateWithLifecycle()
    val yearFieldError = viewModel.yearFieldError.collectAsStateWithLifecycle()
    AddGameScreen(
        navController = navController,
        selectedGame = activityViewModel.selectedGame,
        consoleSelected = activityViewModel.mSelectedConsole,
        gameName = viewModel.gameName,
        setGameName = viewModel::setGameName,
        gameYear = viewModel.gameYear,
        setGameYear = viewModel::setGameYear,
        onClickFab = viewModel::saveOrUpdateGame,
        errorNameFieldMessage = { nameFieldError.value?.let(context::getString).orEmpty() },
        errorYearFieldMessage = { yearFieldError.value?.let(context::getString).orEmpty() },
        successInsert = { successInsert.value.orFalse() },
        cleanSuccessInsert = viewModel::cleanSuccessInsert,
        showSnackBar = { message.value?.let(context::getString).orEmpty() },
        messageShown = viewModel::messageShown
    )
}

@Composable
fun AddGameScreen(
    navController: NavController? = null,
    selectedGame: Game? = null,
    consoleSelected: Console? = null,
    gameName: State<String> = mutableStateOf(String.EMPTY),
    setGameName: FunctionParam<String>? = null,
    gameYear: State<String> = mutableStateOf(String.EMPTY),
    setGameYear: FunctionParam<String>? = null,
    onClickFab: ((Game?, Long) -> Unit)? = null,
    errorNameFieldMessage: FunctionReturn<String> = { String.EMPTY },
    errorYearFieldMessage: FunctionReturn<String> = { String.EMPTY },
    successInsert: FunctionReturn<Boolean> = { false },
    cleanSuccessInsert: FunctionEmpty? = null,
    showSnackBar: FunctionReturn<String?> = { null },
    messageShown: FunctionEmpty? = null
) {
    val scope = rememberCoroutineScope()
    val mSnackBarHostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val message = showSnackBar()
    if (successInsert()) {
        focusRequester.requestFocus()
        setGameName?.invoke(String.EMPTY)
        setGameYear?.invoke(String.EMPTY)
        cleanSuccessInsert?.invoke()
    }
    if (message.orEmpty().isNotEmpty()) {
        LaunchedEffect(mSnackBarHostState) {
            scope.launch {
                mSnackBarHostState.showSnackbar(message.orEmpty())
                messageShown?.invoke()
            }
        }
    }
    Scaffold(
        topBar = {
            AddGameTopBar(
                navController = navController,
                selectedGame = selectedGame
            )
        },
        snackbarHost = { SnackbarHost(hostState = mSnackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onClickFab?.invoke(
                        selectedGame,
                        consoleSelected?.id.orZero()
                    )
                }
            ) {
                Icon(
                    imageVector = if (selectedGame != null) Filled.Edit else Filled.Check,
                    contentDescription = String.EMPTY,
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        AddGameContent(
            paddingValues = innerPadding,
            gameName = gameName,
            setGameName = setGameName,
            gameYear = gameYear,
            setGameYear = setGameYear,
            consoleSelected = consoleSelected,
            nameFieldError = errorNameFieldMessage(),
            yearFieldError = errorYearFieldMessage(),
            focusRequester = focusRequester
        )
    }
}

@Composable
fun AddGameTopBar(
    navController: NavController?,
    selectedGame: Game? = null
) {
    MyGamesTopAppBar(
        mAppBarTitle = {
            LocalContext.current.getString(
                if (selectedGame == null) string.title_add_games else string.title_edit_games
            )
        },
        mNavController = navController
    )
}

@Composable
fun AddGameContent(
    paddingValues: PaddingValues,
    gameName: State<String> = mutableStateOf(String.EMPTY),
    setGameName: FunctionParam<String>? = null,
    gameYear: State<String> = mutableStateOf(String.EMPTY),
    setGameYear: FunctionParam<String>? = null,
    consoleSelected: Console? = null,
    nameFieldError: String = String.EMPTY,
    yearFieldError: String = String.EMPTY,
    focusRequester: FocusRequester,
) {
    Column(Modifier.padding(paddingValues)) {
        MyGamesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .padding(
                    top = dimen4x,
                    start = dimen8x,
                    end = dimen8x
                ),
            value = gameName.value,
            setValue = setGameName,
            error = nameFieldError,
            label = LocalContext.current.getString(string.text_game_name),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )
        MyGamesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimen4x,
                    start = dimen8x,
                    end = dimen8x,
                ),
            value = gameYear.value,
            setValue = setGameYear,
            singleLine = true,
            maxLength = YEAR_LENGTH,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            label = LocalContext.current.getString(string.text_game_year),
            error = yearFieldError
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimen8x,
                    start = dimen8x,
                    end = dimen8x
                ),
            text = LocalContext.current.getString(string.text_console),
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimen8x),
            text = consoleSelected?.name.orEmpty(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddGameScreenPreview() {
    MyGamesTheme {
        AddGameScreen(
            consoleSelected = Console(name = "Super Nintendo")
        )
    }
}