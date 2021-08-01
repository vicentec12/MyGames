package br.com.vicentec12.mygames.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.vicentec12.mygames.MyGamesApp
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.databinding.ActivityGameBinding
import br.com.vicentec12.mygames.di.ViewModelProviderFactory
import br.com.vicentec12.mygames.extensions.viewBinding
import br.com.vicentec12.mygames.ui.add_game.AddGameActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class GameActivity : AppCompatActivity(), ActionMode.Callback {

    @Inject
    lateinit var mFactory: ViewModelProviderFactory

    private val mViewModel: GameViewModel by viewModels { mFactory }

    private val mBinding by viewBinding(ActivityGameBinding::inflate)

    private var mActionMode: ActionMode? = null

    private val mConsole: Console by lazy { intent.getParcelableExtra(EXTRA_CONSOLE) ?: Console() }

    private val mAdapter: GameAdapter by lazy {
        GameAdapter(
                mViewModel,
                { _, mItem, mPosition -> onItemCLick(mItem as Game, mPosition) },
                { _, _, mPosition -> onItemLongCLick(mPosition) }
        ).apply { setHasStableIds(true) }
    }

    private val mActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            setResult(RESULT_OK)
            mViewModel.listSavedGames()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyGamesApp).appComponent.gameComponent().create()
                .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.lytToolbar.toolbar)
        initView()
    }

    override fun onStart() {
        super.onStart()
        if (mViewModel.isSelectionModeVisible() == true && mActionMode == null)
            mActionMode = startSupportActionMode(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                val options = arrayOf(getString(R.string.text_game_name), getString(R.string.text_game_year))
                AlertDialog.Builder(this).setTitle(R.string.title_alert_order_by)
                        .setItems(options) { _, which ->
                            mViewModel.setOrderBy(which)
                            mViewModel.listSavedGames()
                        }.show()
                true
            }
            R.id.action_about -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView() {
        initBinding()
        initRecyclerView()
        initObservers()
    }

    private fun initBinding() {
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this@GameActivity
    }

    private fun initRecyclerView() {
        with(mBinding.rvwGame) {
            if (itemDecorationCount == 0)
                addItemDecoration(DividerItemDecoration(this@GameActivity, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun initObservers() {
        with(mViewModel) {
            selectedItems.observe(this@GameActivity) { mSelections ->
                setTitleActionMode(mSelections.size())
                mBinding.rvwGame.post { mAdapter.notifyDataSetChanged() }
            }
            message.observe(this@GameActivity) { messageEvent ->
                messageEvent.contentIfNotHandled?.let { mMessageId ->
                    Snackbar.make(mBinding.rvwGame, mMessageId, BaseTransientBottomBar.LENGTH_LONG)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                }
            }
            pluralMessage.observe(this@GameActivity) { mPluralMessageEvent ->
                mPluralMessageEvent.contentIfNotHandled?.let {
                    Snackbar.make(mBinding.rvwGame, resources.getQuantityString(it[0], it[1], it[1]),
                            BaseTransientBottomBar.LENGTH_LONG).setAnimationMode(BaseTransientBottomBar
                            .ANIMATION_MODE_SLIDE).show()
                }
            }
            hasActionModeFinish.observe(this@GameActivity) { mHasActionModeFinish ->
                if (mHasActionModeFinish.contentIfNotHandled == true)
                    mActionMode!!.finish()
            }
            setConsole(mConsole)
            listSavedGames()
        }
    }

    private fun setTitleActionMode(mSelectedItemsCount: Int) {
        mActionMode?.title = resources.getQuantityString(R.plurals.plural_selected_games,
                mSelectedItemsCount, mSelectedItemsCount)
    }

    private fun onItemCLick(mGame: Game, mPosition: Int) {
        if (mActionMode == null)
            mActivityResult.launch(AddGameActivity.newIntentInstance(this, mGame))
        else
            mViewModel.select(mPosition)
    }

    private fun onItemLongCLick(mPosition: Int) {
        if (mActionMode == null)
            mActionMode = startSupportActionMode(this)
        mViewModel.select(mPosition)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_action_main, menu)
        mViewModel.showChecks(true)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_main_select_all -> {
                mViewModel.selectAll()
                true
            }
            R.id.action_main_delete -> {
                val mSelectedItemCount = mViewModel.getSelectedItemCount() ?: 0
                if (mSelectedItemCount > 0)
                    AlertDialog.Builder(this).setTitle(R.string.title_alert_warning)
                            .setMessage(resources.getQuantityString(R.plurals.plural_message_warning_delete_game, mSelectedItemCount, mSelectedItemCount))
                            .setPositiveButton(R.string.label_alert_button_yes) { _, _ ->
                                mViewModel.deleteGames()
                                setResult(RESULT_OK)
                            }.setNegativeButton(R.string.label_alert_button_no, null).show()
                else
                    Snackbar.make(mBinding.rvwGame, R.string.message_no_game_select, BaseTransientBottomBar.LENGTH_LONG)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mViewModel.clearSelection()
        mViewModel.showChecks(false)
        mActionMode = null
    }

    companion object {

        private const val EXTRA_CONSOLE = "extra_console"

        fun newIntentInstance(mContext: Context, mConsole: Console) =
                Intent(mContext, GameActivity::class.java).apply {
                    putExtra(EXTRA_CONSOLE, mConsole)
                }

    }

}