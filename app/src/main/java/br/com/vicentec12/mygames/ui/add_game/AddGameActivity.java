package br.com.vicentec12.mygames.ui.add_game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.databinding.ActivityAddGameBinding;
import br.com.vicentec12.mygames.util.InstantiateUtil;
import br.com.vicentec12.mygames.util.ValidationUtil;

public class AddGameActivity extends AppCompatActivity {

    private static final String EXTRA_GAME = "game";
    private static final String EXTRA_CONSOLE = "console";

    private ActivityAddGameBinding mBinding;

    private Game mSelectedGame;
    private Console mSelectedConsole;
    private AddGameViewModel mViewModel;

    public static Intent newIntentInstance(Context context, Game mGame) {
        Intent mIntent = new Intent(context, AddGameActivity.class);
        mIntent.putExtra(EXTRA_GAME, mGame);
        return mIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddGameBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    private void init() {
        mSelectedGame = (Game) getIntent().getSerializableExtra(EXTRA_GAME);
        setupViewModel();
        setupToolbar();
        setupMessage();
        setupEventDatabase();
        mViewModel.listConsoles(mSelectedGame);
    }

    private void setupViewModel() {
        AddGameViewModelFactory mFactory = new AddGameViewModelFactory(InstantiateUtil.initGameRepository(this),
                InstantiateUtil.initConsoleRepository(this));
        mViewModel = new ViewModelProvider(this, mFactory).get(AddGameViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }

    private void setupToolbar() {
        mBinding.lytToolbar.toolbar.setTitle(mSelectedGame == null ? R.string.title_activity_add_games
                : R.string.title_activity_edit_games);
        setSupportActionBar(mBinding.lytToolbar.toolbar);
    }

    private void setupMessage() {
        mViewModel.getMessage().observe(this, integerEvent -> {
            Integer mMessage = integerEvent.getContentIfNotHandled();
            if (mMessage != null)
                Snackbar.make(mBinding.tilAddGameName, getText(mMessage), BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
        });
    }

    private void setupEventDatabase() {
        mViewModel.getEventDatabase().observe(this, booleanEvent -> {
            Boolean mMessage = booleanEvent.getContentIfNotHandled();
            if (mMessage != null) {
                setResult(RESULT_OK);
                if (mViewModel.hasInsert()) {
                    mBinding.tilAddGameName.getEditText().setText("");
                    mBinding.tilAddGameYear.getEditText().setText("");
                    mBinding.tilAddGameName.requestFocus();
                }
            }
        });
    }

    private boolean validateFields() {
        removeFieldErrors();
        if (!ValidationUtil.validateEmptyField(this, mBinding.tilAddGameName))
            return false;
        if (!ValidationUtil.validateEmptyField(this, mBinding.tilAddGameYear))
            return false;
        if (!ValidationUtil.validateSpinner(this, mBinding.spnAddGameConsole, getString(R.string.error_message_spinner_selection)))
            return false;
        return true;
    }

    private void removeFieldErrors() {
        ValidationUtil.removeErrorTextInputLayout(mBinding.tilAddGameName);
        ValidationUtil.removeErrorTextInputLayout(mBinding.tilAddGameYear);
    }

    public void fabEvent(View view) {
        if (validateFields())
            mViewModel.databaseEvent();
    }

}
