package br.com.vicentec12.mygames.presentation.ui.add_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGameFragment : Fragment() {

    private val viewModel: AddGameViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            MyGamesTheme {
                AddGameScreen(
                    navController = findNavController(),
                    viewModel = viewModel,
                    activityViewModel = activityViewModel
                )
            }
        }
    }
}