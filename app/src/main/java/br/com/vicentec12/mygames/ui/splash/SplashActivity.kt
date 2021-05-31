package br.com.vicentec12.mygames.ui.splash

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.MyGamesApp
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.databinding.ActivitySplashBinding
import br.com.vicentec12.mygames.di.ViewModelProviderFactory
import br.com.vicentec12.mygames.ui.console.ConsoleActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mFactory: ViewModelProviderFactory

    private val mViewModel: SplashViewModel by viewModels { mFactory }

    private lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyGamesApp).appComponent.splashComponent().create()
                .inject(this)
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    private fun init() {
        setupViewModel()
    }

    private fun setupViewModel() {
        with(mViewModel) {
            hasFinish.observe(this@SplashActivity) { event ->
                if (event.contentIfNotHandled == true) {
                    startActivity(ConsoleActivity.newIntentInstance(this@SplashActivity))
                    finish()
                }
            }
            message.observe(this@SplashActivity) { messageEvent ->
                messageEvent.contentIfNotHandled?.let { messageId ->
                    AlertDialog.Builder(this@SplashActivity).setTitle(R.string.title_alert_error)
                            .setMessage(messageId).setPositiveButton(R.string.label_alert_button_ok, null).show()
                }
            }
            loadOrCreateConsoles()
        }
    }

}