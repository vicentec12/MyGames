package br.com.vicentec12.mygames.presentation.ui.add_game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
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
import br.com.vicentec12.mygames.presentation.commons.MyGamesTopAppBar
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.presentation.theme.dimen0x
import br.com.vicentec12.mygames.presentation.theme.dimen4x
import br.com.vicentec12.mygames.presentation.theme.dimen8x
import br.com.vicentec12.mygames.util.FunctionReturn
import kotlinx.coroutines.launch

const val YEAR_SIZE = 4

@Composable
fun AddGameScreen(
    mNavController: NavController? = null,
    mGame: Game? = null,
    consoleSelected: Console? = null,
    mTitle: String = String.EMPTY,
    onClickFab: ((Game?, String, String, Long) -> Unit)? = null,
    successInsert: FunctionReturn<Boolean> = { false },
    nameFieldError: FunctionReturn<String> = { String.EMPTY },
    showSnackbar: FunctionReturn<String?> = { null }
) {
    val mScope = rememberCoroutineScope()
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
            mScope.launch {
                println("Mensagem: ${message?.isNotEmpty()}")
                println("Mensagem: $message")
                mSnackbarHostState.showSnackbar(message.orEmpty())
            }
        }
    }
    Scaffold(
        topBar = {
            AddGameTopBar(
                navController = mNavController,
                title = mTitle
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = mSnackbarHostState)
        },
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
                    imageVector = if (mGame != null) Filled.Edit else Filled.Add,
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
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimen4x,
                    start = dimen8x,
                    end = dimen8x,
                    bottom = dimen0x
                )
                .focusRequester(focusRequester),
            value = name?.value.orEmpty(),
            onValueChange = { name?.value = it },
            isError = nameFieldError.isNotEmpty(),
            label = { Text(text = LocalContext.current.getString(string.text_game_name)) }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimen4x,
                    start = dimen8x,
                    end = dimen8x,
                    bottom = dimen0x
                ),
            value = year?.value.orEmpty(),
            onValueChange = {
                if (it.length <= YEAR_SIZE) {
                    year?.value = it
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = { Text(text = LocalContext.current.getString(string.text_game_year)) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddGameScreenPreview() {
    MyGamesTheme {
        AddGameScreen(
            mTitle = "Adicionar jogo"
        )
    }
}