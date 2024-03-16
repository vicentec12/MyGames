package br.com.vicentec12.mygames.presentation.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.vicentec12.mygames.databinding.ActivitySplashBinding
import br.com.vicentec12.mygames.commons.extensions.viewBinding
import br.com.vicentec12.mygames.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val mBinding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        startActivity(MainActivity.newIntentInstance(this@SplashActivity))
        finish()
    }

}