package br.com.vicentec12.mygames.ui.add_game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.MyGamesApp
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.databinding.ActivityAddGameBinding
import br.com.vicentec12.mygames.di.ViewModelProviderFactory
import br.com.vicentec12.mygames.extensions.removeError
import br.com.vicentec12.mygames.extensions.validateEmptyField
import br.com.vicentec12.mygames.extensions.validateQtdCharacters
import br.com.vicentec12.mygames.extensions.validateSelection
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AddGameActivity : AppCompatActivity() {

    @Inject
    lateinit var mFactory: ViewModelProviderFactory

    private val mViewModel: AddGameViewModel by viewModels { mFactory }

    private lateinit var mBinding: ActivityAddGameBinding

    private val mSelectedGame: Game by lazy { intent.getParcelableExtra(EXTRA_GAME) ?: Game() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyGamesApp).appComponent.addGameComponent().create()
                .inject(this)
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddGameBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lytToolbar.toolbar.setTitle(
                    if (mSelectedGame.id == 0L) R.string.title_activity_add_games else R.string.title_activity_edit_games)
            setSupportActionBar(lytToolbar.toolbar)
            viewModel = mViewModel
            lifecycleOwner = this@AddGameActivity
        }
        init()
    }

    private fun init() {
        setupViewModel()
    }

    private fun setupViewModel() {
        with(mViewModel) {
            message.observe(this@AddGameActivity) { mMessageEvent ->
                mMessageEvent.contentIfNotHandled?.let { mMessageId ->
                    Snackbar.make(mBinding.tilAddGameName, mMessageId, BaseTransientBottomBar.LENGTH_LONG)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                }
            }
            success.observe(this@AddGameActivity) { mDataBaseEvent ->
                mDataBaseEvent.contentIfNotHandled?.let {
                    setResult(RESULT_OK)
                    mBinding.tilAddGameName.requestFocus()
                }
            }
            listConsoles(mSelectedGame)
        }
    }

    private fun validateFields(): Boolean {
        removeFieldErrors()
        if (!mBinding.tilAddGameName.validateEmptyField())
            return false
        if (!mBinding.tilAddGameYear.validateEmptyField())
            return false
        if (!mBinding.tilAddGameYear.validateQtdCharacters(4))
            return false
        return mBinding.spnAddGameConsole.validateSelection(getString(R.string.error_message_spinner_selection))
    }

    private fun removeFieldErrors() {
        mBinding.tilAddGameName.removeError()
        mBinding.tilAddGameYear.removeError()
    }

    fun fabEvent(v: View) {
        if (validateFields())
            mViewModel.databaseEvent()
    }

    companion object {

        private const val EXTRA_GAME = "extra_game"

        fun newIntentInstance(mContext: Context, mGame: Game) = Intent(mContext, AddGameActivity::class.java).apply {
            putExtra(EXTRA_GAME, mGame)
        }

    }

}