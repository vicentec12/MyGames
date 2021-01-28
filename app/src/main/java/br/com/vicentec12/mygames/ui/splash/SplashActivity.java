package br.com.vicentec12.mygames.ui.splash;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import br.com.vicentec12.mygames.MyGamesApp;
import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.databinding.ActivitySplashBinding;
import br.com.vicentec12.mygames.di.ViewModelProviderFactory;
import br.com.vicentec12.mygames.ui.console.ConsoleActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mBinding;

    @Inject
    public ViewModelProviderFactory mFactory;
    private SplashViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyGamesApp) getApplicationContext()).getAppComponent().
                splashComponent().create().inject(this);
        super.onCreate(savedInstanceState);
        mBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    private void init() {
        setupViewModel();
        setupHasFinish();
        setupMessage();
        mViewModel.initSplash();
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this, mFactory).get(SplashViewModel.class);
    }

    private void setupHasFinish() {
        mViewModel.getHasFinish().observe(this, booleanEvent -> {
            boolean hasFinish = booleanEvent.getContentIfNotHandled();
            if (hasFinish) {
                startActivity(ConsoleActivity.newIntentInstance(SplashActivity.this));
                finish();
            }
        });
    }

    private void setupMessage() {
        mViewModel.getMessage().observe(this, integerEvent -> {
            int mMessage = integerEvent.getContentIfNotHandled();
            new AlertDialog.Builder(SplashActivity.this).setTitle(R.string.title_alert_error)
                    .setMessage(mMessage).setPositiveButton(R.string.label_alert_button_ok, null).show();
        });
    }

}