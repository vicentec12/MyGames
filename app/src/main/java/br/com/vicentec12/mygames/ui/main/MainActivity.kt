package br.com.vicentec12.mygames.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.databinding.ActivityMainBinding
import br.com.vicentec12.mygames.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mBinding by viewBinding(ActivityMainBinding::inflate)

    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(mBinding.lytToolbar.toolbar)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fcv_nav_host_main) as NavHostFragment
        setupActionBarWithNavController(navHostFragment.navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.fcv_nav_host_main).navigateUp() ||
            super.onSupportNavigateUp()

    companion object {
        fun newIntentInstance(mContext: Context) = Intent(mContext, MainActivity::class.java)
    }

}