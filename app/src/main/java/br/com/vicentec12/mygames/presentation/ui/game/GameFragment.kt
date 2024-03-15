package br.com.vicentec12.mygames.presentation.ui.game

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.makeSnackbar
import br.com.vicentec12.mygames.extensions.navigateWithAnim
import br.com.vicentec12.mygames.extensions.orZero
import br.com.vicentec12.mygames.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment(), ActionMode.Callback {

    private val mViewModel: GameViewModel by viewModels()
    private val mActivityViewModel: MainViewModel by activityViewModels()
    private var mActionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            val uiState = mViewModel.uiState.collectAsStateWithLifecycle()
            val isSelectionMode = mViewModel.selectionMode.collectAsStateWithLifecycle()
            val selectedItems = mViewModel.selectedItems.collectAsStateWithLifecycle()
            val pluralMessage = mViewModel.pluralMessage.collectAsStateWithLifecycle()
            val message = mViewModel.message.collectAsStateWithLifecycle()
            GameScreen(
                navController = findNavController(),
                uiState = uiState.value,
                isSelectionMode = { isSelectionMode.value },
                selectedItems = { selectedItems.value },
                onItemClickListener = ::onItemClick,
                onItemLongClickListener = { _, position -> onItemLongClick(position) },
                onItemOrderByClick = ::onItemOrderByClick,
                onClickFab = ::navigateToAddGame,
                showSnackbar = {
                    getSnackBarPluralMessage(pluralMessage.value)
                        ?: getSnackBarMessage(message.value)
                }
            )
            setupActionMode(isSelectionMode.value)
            setTitleActionMode(selectedItems.value)
        }
    }

    override fun onStart() {
        super.onStart()
        mViewModel.listSavedGames(mActivityViewModel.mSelectedConsole)
    }

    private fun setTitleActionMode(mSelectedItems: SparseBooleanArray?) {
        val mSelectedItemsCount: Int = mSelectedItems?.size().orZero()
        mActionMode?.title = resources.getQuantityString(
            R.plurals.plural_selected_games,
            mSelectedItemsCount,
            mSelectedItemsCount
        )
    }

    private fun onItemClick(mGame: Game?, mPosition: Int) {
        if (mActionMode == null) {
            navigateToAddGame(mGame)
        } else {
            mViewModel.select(mPosition)
        }
    }

    private fun onItemLongClick(mPosition: Int) {
        startSupportActionMode()
        mViewModel.initSelectionMode(mPosition)
    }

    private fun onItemOrderByClick() {
        AlertDialog.Builder(requireContext()).setTitle(R.string.title_alert_order_by)
            .setItems(R.array.game_order_by_options) { _, which ->
                mViewModel.setOrderBy(which)
                mViewModel.listSavedGames(mActivityViewModel.mSelectedConsole)
            }.show()
    }

    private fun getSnackBarMessage(pluralMessage: Pair<Int, Int>?, message: String?) {
        if (pluralMessage != null) {

        }
        pluralMessage?.let {
            mViewModel.pluralMessageShown()

        } ?: message.orEmpty()
    }

    private fun getSnackBarMessage(mMessage: Int?) =
        if (mMessage != null) {
            val messageValue = getString(mMessage)
            mViewModel.messageShown()
            messageValue
        } else {
            null
        }

    private fun getSnackBarPluralMessage(message: Pair<Int, Int>?) =
        if (message != null) {
            val messageValue =
                resources.getQuantityString(message.first, message.second, message.second)
            mViewModel.pluralMessageShown()
            messageValue
        } else {
            null
        }

    private fun setupActionMode(isActionMode: Boolean) {
        if (isActionMode) {
            startSupportActionMode()
        } else {
            mActionMode?.finish()
        }
    }

    private fun navigateToAddGame(mGame: Game? = null) {
        mActivityViewModel.mSelectedGame = mGame
        requireView().findNavController()
            .navigateWithAnim(GameFragmentDirections.navigateAddGame())
    }

    private fun startSupportActionMode() {
        if (mActionMode == null) {
            mActionMode = (activity as? AppCompatActivity)?.startSupportActionMode(this)
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_action_main, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = when (item?.itemId) {
        R.id.action_main_select_all -> {
            mViewModel.selectAll()
            true
        }

        R.id.action_main_delete -> {
            val mSelectedItemCount = mViewModel.getSelectedItemCount()
            if (mSelectedItemCount > 0)
                AlertDialog.Builder(requireContext()).setTitle(R.string.title_alert_warning)
                    .setMessage(
                        resources.getQuantityString(
                            R.plurals.plural_message_warning_delete_game,
                            mSelectedItemCount, mSelectedItemCount
                        )
                    )
                    .setPositiveButton(R.string.label_alert_button_yes) { _, _ -> mViewModel.deleteGames() }
                    .setNegativeButton(R.string.label_alert_button_no, null).show()
            else
                makeSnackbar(R.string.message_no_game_select).show()
            true
        }

        else -> false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mViewModel.finishSelectionMode()
        mActionMode = null
    }
}
