package br.com.vicentec12.mygames.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.R.string
import br.com.vicentec12.mygames.extensions.EMPTY
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.util.FunctionReturn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGamesTopAppBar(
    mAppBarTitle: @Composable FunctionReturn<String> = { String.EMPTY },
    mActions: @Composable RowScope.() -> Unit = {},
    mNavController: NavController? = null
) {
    TopAppBar(
        title = {
            Text(
                text = mAppBarTitle(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            if (mNavController?.previousBackStackEntry != null) {
                NavigationIcon(mNavController)
            }
        },
        actions = mActions
    )
}

@Composable
fun NavigationIcon(
    mNavController: NavController? = null
) {
    IconButton(onClick = { mNavController?.navigateUp() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = LocalContext.current.getString(R.string.text_back),
            tint = Color.White
        )
    }
}

@Composable
@Preview
fun MyGamesTopAppBarPreview() {
    MyGamesTheme {
        MyGamesTopAppBar(
            mAppBarTitle = { "MyGames" },
            mActions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Filled.Add,
                        tint = Color.White,
                        contentDescription = LocalContext.current.getString(string.text_back)
                    )
                }
            }
        )
    }
}