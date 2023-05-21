package br.com.vicentec12.mygames.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import br.com.vicentec12.mygames.databinding.AppFragmentContainerViewBinding
import br.com.vicentec12.mygames.ui.commons.MyGamesTopAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    appBarTitle: () -> String?,
    appBarNavigationIconClick: () -> Unit,
    isShownAppBarNavigationIcon: () -> Boolean?,
    navController: (NavController?) -> Unit
) {
    Scaffold(
        topBar = {
            MyGamesTopAppBar(appBarTitle, appBarNavigationIconClick, isShownAppBarNavigationIcon)
        },
        content = { MainContent(navController) }
    )
}

@Composable
fun MainContent(navController: (NavController?) -> Unit) {
    AndroidViewBinding(AppFragmentContainerViewBinding::inflate) {
        navController(
            try {
                fcvNavHostMain.findNavController()
            } catch (e: IllegalStateException) {
                Log.getStackTraceString(e)
                null
            }
        )
    }
}