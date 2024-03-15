package br.com.vicentec12.mygames.presentation.ui.console

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import br.com.vicentec12.mygames.extensions.navigateWithAnim
import br.com.vicentec12.mygames.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsoleFragment : Fragment() {

    private val mViewModel: ConsoleViewModel by viewModels()
    private val mActivityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            val uiState by mViewModel.uiState.observeAsState()
            ConsoleScreen(
                uiState = uiState,
                onItemClick = { mConsole, _ ->
                    if (mConsole != null) {
                        mActivityViewModel.mSelectedConsole = mConsole
                        findNavController()
                            .navigateWithAnim(ConsoleFragmentDirections.navigateGame())
                    }
                },
            )
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.listConsoles()
    }

}