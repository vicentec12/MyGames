package br.com.vicentec12.mygames.ui.views

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import br.com.vicentec12.mygames.extensions.orFalse

@Composable
fun MyGamesTopAppBar(
    appBarTitle: () -> String?,
    appBarNavigationIconClick: () -> Unit,
    isShownAppBarNavigationIcon: Boolean?,
) {
    TopAppBar(
        title = { Text(text = appBarTitle().orEmpty()) },
        navigationIcon = if (isShownAppBarNavigationIcon.orFalse()) {
            { NavigationIcon(appBarNavigationIconClick) }
        } else null
    )
}

@Composable
fun NavigationIcon(appBarNavigationIconClick: () -> Unit) {
    IconButton(onClick = { appBarNavigationIconClick() }) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
    }
}