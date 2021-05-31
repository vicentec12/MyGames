package br.com.vicentec12.mygames.ui.console

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.MyGamesApp
import br.com.vicentec12.mygames.data.model.ConsoleWithGames
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
            startActivityForResult(GameActivity.newIntentInstance(this@ConsoleActivity,
                    (consoleWithGames as ConsoleWithGames).console), CODE_OPERATION_SUCCESS)
        }
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
        startActivityForResult(AddGameActivity.newIntentInstance(this, null),
                CODE_OPERATION_SUCCESS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_OPERATION_SUCCESS) {
            if (resultCode == RESULT_OK)
                mViewModel.listConsoles()
        }
    }

    companion object {

        const val CODE_OPERATION_SUCCESS = 2907

        fun newIntentInstance(mContext: Context) = Intent(mContext, ConsoleActivity::class.java)

    }

}