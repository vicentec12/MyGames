package br.com.vicentec12.mygames.ui.add_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.databinding.FragmentAddGameBinding
import br.com.vicentec12.mygames.extensions.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGameFragment : Fragment() {

    private val mViewModel: AddGameViewModel by viewModels()

    private val mBinding by viewBinding(FragmentAddGameBinding::inflate)

    private val mArgs: AddGameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initBinding()
        initToolbar()
        initObservers()
        initListeners()
    }

    private fun initBinding() {
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this
    }

    private fun initToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(getTitle())
    }

    private fun initObservers() {
        with(mViewModel) {
            message.observe(viewLifecycleOwner) { mMessageEvent ->
                mMessageEvent.contentIfNotHandled?.let { mMessageId -> makeSnackbar(mMessageId).show() }
            }
            success.observe(viewLifecycleOwner) { mDataBaseEvent ->
                mDataBaseEvent.contentIfNotHandled?.let { mBinding.tilAddGameName.requestFocus() }
            }
            consoles.observe(viewLifecycleOwner) { setGame(mArgs.selectedGame) }
            listConsoles()
        }
    }

    private fun initListeners() {
        mBinding.fabAddGame.setOnClickListener {
            if (validateFields())
                mViewModel.databaseEvent()
        }
    }

    private fun validateFields(): Boolean {
        with(mBinding) {
            removeFieldErrors()
            return when {
                !tilAddGameName.validateEmptyField() -> false
                !tilAddGameYear.validateEmptyField() -> false
                !tilAddGameYear.validateQtdCharacters(YEAR_QUANTITY) -> false
                !spnAddGameConsole.validateSelection(getString(R.string.error_message_spinner_selection)) -> false
                else -> true
            }
        }
    }

    private fun removeFieldErrors() {
        mBinding.tilAddGameName.removeError()
        mBinding.tilAddGameYear.removeError()
    }

    private fun getTitle() =
        if (mArgs.selectedGame.id == 0L) R.string.title_add_games
        else R.string.title_edit_games

}