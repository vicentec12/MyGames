package br.com.vicentec12.mygames.ui.main

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavController
import androidx.navigation.findNavController
import br.com.vicentec12.mygames.databinding.AppFragmentContainerViewBinding
import br.com.vicentec12.mygames.ui.views.MyGamesTopAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    appBarTitle: () -> String?,
    appBarNavigationIconClick: () -> Unit,
    isShownAppBarNavigationIcon: Boolean?,
    navController: (NavController?) -> Unit
) {
    Scaffold(
        topBar = {
            MyGamesTopAppBar(appBarTitle, appBarNavigationIconClick, isShownAppBarNavigationIcon)
        },
        content = {
            AndroidViewBinding(AppFragmentContainerViewBinding::inflate) {
                navController(
                    try {
                        fcvNavHostMain.findNavController()
                    } catch (e: IllegalStateException) {
                        null
                    }
                )
            }
        }
    )
}