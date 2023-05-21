package br.com.vicentec12.mygames.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import br.com.vicentec12.mygames.ui.theme.MyGamesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()

    private var mNavController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGamesTheme {
                val mAppBarTitle = mViewModel.appBarTitle.observeAsState()
                val mIsShowAppBarNavigationIcon =
                    mViewModel.isShownAppBarNavigationIcon.observeAsState()
                MainScreen(
                    appBarTitle = { mAppBarTitle.value },
                    appBarNavigationIconClick = { mNavController?.navigateUp() },
                    isShownAppBarNavigationIcon = { mIsShowAppBarNavigationIcon.value },
                    navController = ::addOnDestinationChangedListener
                )
            }
        }
    }

    private fun addOnDestinationChangedListener(navController: NavController?) {
        mNavController = navController
        mNavController?.addOnDestinationChangedListener { controller, dest, _ ->
            mViewModel.configAppBar(
                dest.label.toString(),
                controller.previousBackStackEntry != null
            )
        }
    }

    companion object {
        fun newIntentInstance(mContext: Context) = Intent(mContext, MainActivity::class.java)
    }

}