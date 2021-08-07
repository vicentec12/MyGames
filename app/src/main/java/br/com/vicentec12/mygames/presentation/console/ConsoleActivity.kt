package br.com.vicentec12.mygames.presentation.console

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.data.local.entities.GameEntity
import br.com.vicentec12.mygames.databinding.ActivityConsoleBinding
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.viewBinding
import br.com.vicentec12.mygames.presentation.add_game.AddGameActivity
import br.com.vicentec12.mygames.presentation.game.GameActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsoleActivity : AppCompatActivity() {

    private val mViewModel: ConsoleViewModel by viewModels()

    private val mBinding by viewBinding(ActivityConsoleBinding::inflate)

    private val mAdapter: ConsoleAdapter by lazy {
        ConsoleAdapter { _, console, _ ->
            mActivityResult.launch(
                GameActivity.newIntentInstance(this@ConsoleActivity, console as Console)
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

    override fun onStart() {
        super.onStart()
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
            lifecycleOwner = this@ConsoleActivity
            rvwConsole.adapter = mAdapter
        }
    }

    private fun initListeners() {
        mBinding.fabConsoleAddGames.setOnClickListener {
            mActivityResult.launch(AddGameActivity.newIntentInstance(this, Game()))
        }
    }

    companion object {

        fun newIntentInstance(mContext: Context) = Intent(mContext, ConsoleActivity::class.java)

    }

}