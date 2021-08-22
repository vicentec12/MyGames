package br.com.vicentec12.mygames.presentation.splash

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.databinding.ActivitySplashBinding
import br.com.vicentec12.mygames.extensions.viewBinding
import br.com.vicentec12.mygames.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val mViewModel: SplashViewModel by viewModels()

    private val mBinding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        initObservers()
    }

    private fun initObservers() {
        with(mViewModel) {
            hasFinish.observe(this@SplashActivity) { event ->
                if (event.contentIfNotHandled == true) {
                    startActivity(MainActivity.newIntentInstance(this@SplashActivity))
                    finish()
                }
            }
            message.observe(this@SplashActivity) { messageEvent ->
                messageEvent.contentIfNotHandled?.let { messageId ->
                    AlertDialog.Builder(this@SplashActivity).setTitle(R.string.title_alert_error)
                        .setMessage(messageId)
                        .setPositiveButton(R.string.label_alert_button_ok, null).show()
                }
            }
            loadOrCreateConsoles()
        }
    }

}