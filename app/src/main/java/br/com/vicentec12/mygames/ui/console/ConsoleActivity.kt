package br.com.vicentec12.mygames.ui.console

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.data.model.ConsoleWithGames
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.databinding.ActivityConsoleBinding
import br.com.vicentec12.mygames.extensions.viewBinding
import br.com.vicentec12.mygames.ui.add_game.AddGameActivity
import br.com.vicentec12.mygames.ui.game.GameActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsoleActivity : AppCompatActivity() {

    private val mViewModel: ConsoleViewModel by viewModels()

    private val mBinding by viewBinding(ActivityConsoleBinding::inflate)

    private val mAdapter: ConsoleAdapter by lazy {
        ConsoleAdapter { _, consoleWithGames, _ ->
            mActivityResult.launch(
                GameActivity.newIntentInstance(
                    this@ConsoleActivity,
                    (consoleWithGames as ConsoleWithGames).console
                )
            )
        }
    }

    private val mActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK)
                mViewModel.listConsoles()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.lytToolbar.toolbar)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.rvwConsole.adapter = null
    }

    private fun initView() {
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this@ConsoleActivity
        mBinding.rvwConsole.adapter = mAdapter
        mViewModel.listConsoles()
    }

    fun openAddGame(v: View) {
        mActivityResult.launch(AddGameActivity.newIntentInstance(this, Game()))
    }

    companion object {

        fun newIntentInstance(mContext: Context) = Intent(mContext, ConsoleActivity::class.java)

    }

}