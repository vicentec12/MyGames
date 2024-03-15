package br.com.vicentec12.mygames.presentation.ui.add_game

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.vicentec12.mygames.R.string
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.EMPTY
import br.com.vicentec12.mygames.extensions.orZero
import br.com.vicentec12.mygames.presentation.components.MyGamesOutlinedTextField
import br.com.vicentec12.mygames.presentation.components.MyGamesTopAppBar
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.presentation.theme.dimen0x
import br.com.vicentec12.mygames.presentation.theme.dimen4x
import br.com.vicentec12.mygames.presentation.theme.dimen8x
import br.com.vicentec12.mygames.util.FunctionReturn
import kotlinx.coroutines.launch

const val YEAR_LENGTH = 4

@Composable
fun AddGameScreen(
    navController: NavController? = null,
    mGame: Game? = null,
    consoleSelected: Console? = null,
    title: String = String.EMPTY,
    onClickFab: ((Game?, String, String, Long) -> Unit)? = null,
    successInsert: FunctionReturn<Boolean> = { false },
    nameFieldError: FunctionReturn<String> = { String.EMPTY },
    yearFieldError: FunctionReturn<String> = { String.EMPTY },
    showSnackbar: FunctionReturn<String?> = { null }
) {
    val scope = rememberCoroutineScope()
    val mSnackbarHostState = remember { SnackbarHostState() }
    val name = remember { mutableStateOf(mGame?.name.orEmpty()) }
    val year = remember { mutableStateOf(mGame?.year.orEmpty()) }
    val focusRequester = remember { FocusRequester() }
    val message = showSnackbar()
    if (successInsert()) {
        focusRequester.requestFocus()
        name.value = String.EMPTY
        year.value = String.EMPTY
    }
    if (message.orEmpty().isNotEmpty()) {
        LaunchedEffect(mSnackbarHostState) {
            scope.launch { mSnackbarHostState.showSnackbar(message.orEmpty()) }
        }
    }
    Scaffold(
        topBar = {
            AddGameTopBar(
                navController = navController,
                title = title
            )
        },
        snackbarHost = { SnackbarHost(hostState = mSnackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onClickFab?.invoke(
                        mGame,
                        name.value,
                        year.value,
                        consoleSelected?.id.orZero()
                    )
                }
            ) {
                Icon(
                    imageVector = if (mGame != null) Filled.Edit else Filled.Check,
                    contentDescription = String.EMPTY,
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        AddGameContent(
            paddingValues = innerPadding,
            name = name,
            year = year,
            consoleSelected = consoleSelected,
            nameFieldError = nameFieldError(),
            yearFieldError = yearFieldError(),
            focusRequester = focusRequester
        )
    }
}

@Composable
fun AddGameTopBar(
    navController: NavController?,
    title: String
) {
    MyGamesTopAppBar(
        mAppBarTitle = { title },
        mNavController = navController
    )
}

@Composable
fun AddGameContent(
    paddingValues: PaddingValues,
    name: MutableState<String>? = null,
    year: MutableState<String>? = null,
    consoleSelected: Console? = null,
    nameFieldError: String = String.EMPTY,
    yearFieldError: String = String.EMPTY,
    focusRequester: FocusRequester,
) {
    Column(
        Modifier.padding(paddingValues)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(
                    top = dimen8x,
                    start = dimen8x,
                    end = dimen8x,
                    bottom = dimen0x
                )
                .fillMaxWidth(),
            value = consoleSelected?.name.orEmpty(),
            onValueChange = { },
            enabled = false,
            label = { Text(text = LocalContext.current.getString(string.text_console)) }
        )
        MyGamesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimen4x,
                    start = dimen8x,
                    end = dimen8x
                )
                .focusRequester(focusRequester),
            value = name,
            error = nameFieldError,
            label = LocalContext.current.getString(string.text_game_name)
        )
        MyGamesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimen4x,
                    start = dimen8x,
                    end = dimen8x,
                ),
            value = year,
            singleLine = true,
            maxLength = YEAR_LENGTH,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = LocalContext.current.getString(string.text_game_year),
            error = yearFieldError
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddGameScreenPreview() {
    MyGamesTheme {
        AddGameScreen(
            title = "Adicionar jogo"
        )
    }
}