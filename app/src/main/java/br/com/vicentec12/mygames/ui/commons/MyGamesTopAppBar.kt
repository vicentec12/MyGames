package br.com.vicentec12.mygames.ui.commons

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.vicentec12.mygames.extensions.EMPTY
import br.com.vicentec12.mygames.extensions.orFalse
import br.com.vicentec12.mygames.ui.theme.MyGamesTheme
import br.com.vicentec12.mygames.util.FunctionReturn

@Composable
fun MyGamesTopAppBar(
    appBarTitle: @Composable FunctionReturn<String> = { String.EMPTY },
    actions: @Composable RowScope.() -> Unit = {},
    isShownAppBarNavigationIcon: FunctionReturn<Boolean> = { false },
    navController: NavController = rememberNavController()
) {
    TopAppBar(
        title = {
            Text(
                text = appBarTitle(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = if (isShownAppBarNavigationIcon().orFalse()) {
            { NavigationIcon(navController) }
        } else null,
        actions = actions
    )
}

@Composable
fun NavigationIcon(
    navController: NavController,
) {
    IconButton(onClick = { navController.navigateUp() }) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
    }
}

@Composable
@Preview
fun MyGamesTopAppBarPreview() {
    MyGamesTheme {
        MyGamesTopAppBar(
            appBarTitle = { "MyGames" },
            isShownAppBarNavigationIcon = { true },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        tint = Color.White,
                        contentDescription = "Voltar"
                    )
                }
            }
        )
    }
}