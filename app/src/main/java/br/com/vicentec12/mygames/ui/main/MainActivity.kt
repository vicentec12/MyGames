package br.com.vicentec12.mygames.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.viewinterop.AndroidViewBinding
import br.com.vicentec12.mygames.databinding.AppFragmentContainerViewBinding
import br.com.vicentec12.mygames.ui.theme.MyGamesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGamesTheme {
                AndroidViewBinding(AppFragmentContainerViewBinding::inflate)
            }
        }
    }

    companion object {
        fun newIntentInstance(mContext: Context) = Intent(mContext, MainActivity::class.java)
    }

}