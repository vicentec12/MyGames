package br.com.vicentec12.mygames.ui.console

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.navigateWithAnim
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsoleFragment : Fragment() {

    private val mViewModel: ConsoleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            val uiState by mViewModel.uiState.observeAsState()
            ConsoleScreen(
                mUiState = uiState,
                mOnItemClick = { mConsole, _ -> navigateItemFragment(mConsole) },
                mFabOnClick = {
                    requireView().findNavController()
                        .navigateWithAnim(ConsoleFragmentDirections.navigateAddGame(Game()))
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.listConsoles()
    }

    private fun navigateItemFragment(mConsole: Console?) {
        if (mConsole != null) {
            requireView().findNavController()
                .navigateWithAnim(ConsoleFragmentDirections.navigateGame(mConsole))
        }
    }

}