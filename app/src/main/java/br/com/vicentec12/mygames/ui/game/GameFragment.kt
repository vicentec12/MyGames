package br.com.vicentec12.mygames.ui.game

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.databinding.FragmentGameBinding
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.makeSnackbar
import br.com.vicentec12.mygames.extensions.navigateWithAnim
import br.com.vicentec12.mygames.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment(), ActionMode.Callback {

    private val mBinding by viewBinding(FragmentGameBinding::inflate)

    private val mViewModel: GameViewModel by viewModels()

    private val mArgs: GameFragmentArgs by navArgs()

    private var mActionMode: ActionMode? = null

    private val mAdapter: GameAdapter by lazy {
        GameAdapter(mViewModel, ::onItemCLick) { _, mPosition ->
            onItemLongCLick(mPosition)
        }.apply { setHasStableIds(true) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            val uiState = mViewModel.uiState.collectAsState()
            GameScreen(mUiState = uiState.value, mOnItemClickListener = ::onItemCLick)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        if (mViewModel.selectionMode.value == true)
            startSupportActionMode()
    }

    private fun initMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
                R.id.action_sort -> {
                    AlertDialog.Builder(requireContext()).setTitle(R.string.title_alert_order_by)
                        .setItems(R.array.game_order_by_options) { _, which ->
                            mViewModel.setOrderBy(which)
                            mViewModel.listSavedGames()
                        }.show()
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initView() {
        initBinding()
        initMenu()
        initRecyclerView()
        initObservers()
    }

    private fun initBinding() {
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        with(mBinding.rvwGame) {
            if (itemDecorationCount == 0)
                addItemDecoration(
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                )
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun initObservers() {
        with(mViewModel) {
            selectedItems.observe(viewLifecycleOwner) { mSelections ->
                setTitleActionMode(mSelections.size())
                mBinding.rvwGame.post { mAdapter.notifyDataSetChanged() }
            }
            message.observe(viewLifecycleOwner) { messageEvent ->
                messageEvent.contentIfNotHandled?.let { mMessageId -> makeSnackbar(mMessageId).show() }
            }
            pluralMessage.observe(viewLifecycleOwner) { mPluralMessageEvent ->
                mPluralMessageEvent.contentIfNotHandled?.let {
                    makeSnackbar(resources.getQuantityString(it.first, it.second, it.second)).show()
                }
            }
            hasActionModeFinish.observe(viewLifecycleOwner) { mHasActionModeFinish ->
                if (mHasActionModeFinish.contentIfNotHandled == true)
                    mActionMode!!.finish()
            }
            setConsole(mArgs.console)
            listSavedGames()
        }
    }

    private fun setTitleActionMode(mSelectedItemsCount: Int) {
        mActionMode?.title = resources.getQuantityString(
            R.plurals.plural_selected_games,
            mSelectedItemsCount, mSelectedItemsCount
        )
    }

    private fun startSupportActionMode() {
        if (mActionMode == null) {
            mActionMode = (activity as? AppCompatActivity)?.startSupportActionMode(this)
        }
    }

    private fun onItemCLick(mGame: Game?, mPosition: Int) {
        if (mGame != null) {
            if (mActionMode == null) {
                requireView().findNavController()
                    .navigateWithAnim(GameFragmentDirections.navigateAddGame(mGame))
            } else {
                mViewModel.select(mPosition)
            }
        }
    }

    private fun onItemLongCLick(mPosition: Int) {
        startSupportActionMode()
        mViewModel.select(mPosition)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_action_main, menu)
        mViewModel.setSelectionModeVisible(true)
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
        mViewModel.clearSelection()
        mViewModel.setSelectionModeVisible(false)
        mActionMode = null
    }
}
