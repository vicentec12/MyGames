package br.com.vicentec12.mygames.presentation.console

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import br.com.vicentec12.mygames.databinding.FragmentConsoleBinding
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.navigateWithAnim
import br.com.vicentec12.mygames.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsoleFragment : Fragment() {

    private val mBinding by viewBinding(FragmentConsoleBinding::inflate)

    private val mViewModel: ConsoleViewModel by viewModels()

    private val mAdapter: ConsoleAdapter by lazy {
        ConsoleAdapter { mView, mConsole, _ ->
            mView.findNavController()
                .navigateWithAnim(ConsoleFragmentDirections.navigateGame(mConsole as Console))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.listConsoles()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.rvwConsole.adapter = null
    }

    private fun initView() {
        initBinding()
        initListeners()
    }

    private fun initBinding() {
        with(mBinding) {
            viewModel = mViewModel
            lifecycleOwner = this@ConsoleFragment
            rvwConsole.adapter = mAdapter
        }
    }

    private fun initListeners() {
        mBinding.fabConsoleAddGames.setOnClickListener { mView ->
            mView.findNavController()
                .navigateWithAnim(ConsoleFragmentDirections.navigateAddGame(Game()))
        }
    }

}