package br.com.vicentec12.mygames.ui.console

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.MyGamesApp
import br.com.vicentec12.mygames.data.model.ConsoleWithGames
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.databinding.ActivityConsoleBinding
import br.com.vicentec12.mygames.di.ViewModelProviderFactory
import br.com.vicentec12.mygames.ui.add_game.AddGameActivity
import br.com.vicentec12.mygames.ui.game.GameActivity
import javax.inject.Inject

class ConsoleActivity : AppCompatActivity() {

    @Inject
    lateinit var mFactory: ViewModelProviderFactory

    private val mViewModel: ConsoleViewModel by viewModels { mFactory }

    private lateinit var mBinding: ActivityConsoleBinding

    private val mAdapter: ConsoleAdapter by lazy {
        ConsoleAdapter { _, consoleWithGames, _ ->
            activityResult.launch(GameActivity.newIntentInstance(this@ConsoleActivity,
                    (consoleWithGames as ConsoleWithGames).console))
        }
    }

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK)
            mViewModel.listConsoles()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyGamesApp).appComponent.consoleComponent().create()
                .inject(this)
        super.onCreate(savedInstanceState)
        mBinding = ActivityConsoleBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setSupportActionBar(lytToolbar.toolbar)
            viewModel = mViewModel
            lifecycleOwner = this@ConsoleActivity
        }
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.rvwConsole.adapter = null
    }

    private fun init() {
        mBinding.rvwConsole.adapter = mAdapter
        mViewModel.listConsoles()
    }

    fun openAddGame(v: View) {
        activityResult.launch(AddGameActivity.newIntentInstance(this, Game()))
    }

    companion object {

        fun newIntentInstance(mContext: Context) = Intent(mContext, ConsoleActivity::class.java)

    }

}