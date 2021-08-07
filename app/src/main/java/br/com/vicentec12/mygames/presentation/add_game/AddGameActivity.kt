package br.com.vicentec12.mygames.presentation.add_game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.databinding.ActivityAddGameBinding
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGameActivity : AppCompatActivity() {

    private val mViewModel: AddGameViewModel by viewModels()

    private val mBinding by viewBinding(ActivityAddGameBinding::inflate)

    private val mSelectedGame: Game by lazy { intent.getParcelableExtra(EXTRA_GAME) ?: Game() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
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
        mBinding.lifecycleOwner = this@AddGameActivity
    }

    private fun initToolbar() {
        with(mBinding.lytToolbar.toolbar) {
            setTitle(if (mSelectedGame.id == 0L) R.string.title_activity_add_games else R.string.title_activity_edit_games)
            setSupportActionBar(this)
        }
    }

    private fun initObservers() {
        with(mViewModel) {
            message.observe(this@AddGameActivity) { mMessageEvent ->
                mMessageEvent.contentIfNotHandled?.let { mMessageId ->
                    Snackbar.make(
                        mBinding.tilAddGameName,
                        mMessageId,
                        BaseTransientBottomBar.LENGTH_LONG
                    )
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

    companion object {

        private const val EXTRA_GAME = "extra_game"

        fun newIntentInstance(mContext: Context, mGame: Game) =
            Intent(mContext, AddGameActivity::class.java).apply {
                putExtra(EXTRA_GAME, mGame)
            }

    }

}