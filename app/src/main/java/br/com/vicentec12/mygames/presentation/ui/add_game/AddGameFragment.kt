package br.com.vicentec12.mygames.presentation.ui.add_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.extensions.orFalse
import br.com.vicentec12.mygames.presentation.theme.MyGamesTheme
import br.com.vicentec12.mygames.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGameFragment : Fragment() {

    private val mViewModel: AddGameViewModel by viewModels()
    private val mActivityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            val mSuccessInsert = mViewModel.successInsert.collectAsStateWithLifecycle()
            val mMessage = mViewModel.message.collectAsStateWithLifecycle()
            val mNameFieldError = mViewModel.nameFieldError.collectAsStateWithLifecycle()
            MyGamesTheme {
                AddGameScreen(
                    mNavController = findNavController(),
                    mGame = mActivityViewModel.mSelectedGame,
                    consoleSelected = mActivityViewModel.mSelectedConsole,
                    mTitle = getTitle(),
                    onClickFab = mViewModel::saveOrUpdateGame,
                    successInsert = {
                        val mSuccessInsertValue = mSuccessInsert.value.orFalse()
                        mViewModel.successInsert()
                        mSuccessInsertValue
                    },
                    nameFieldError = {
                        mNameFieldError.value?.let(::getString).orEmpty()
                    },
                    showSnackbar = {
                        val mMessageValue = mMessage.value
                        mViewModel.messageShown()
                        mMessageValue?.let(::getString).orEmpty()
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivityViewModel.mSelectedGame = null
    }

//    private fun validateFields(): Boolean {
//        with(mBinding) {
//            removeFieldErrors()
//            return when {
//                !tilAddGameName.validateEmptyField() -> false
//                !tilAddGameYear.validateEmptyField() -> false
//                !tilAddGameYear.validateQtdCharacters(YEAR_QUANTITY) -> false
//                !spnAddGameConsole.validateSelection(getString(R.string.error_message_spinner_selection)) -> false
//                else -> true
//            }
//        }
//    }

//    private fun removeFieldErrors() {
//        mBinding.tilAddGameName.removeError()
//        mBinding.tilAddGameYear.removeError()
//    }

    private fun getTitle() = getString(
        if (mActivityViewModel.mSelectedGame == null) R.string.title_add_games else R.string.title_edit_games
    )
}