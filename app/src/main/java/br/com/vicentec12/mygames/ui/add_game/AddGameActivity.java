package br.com.vicentec12.mygames.ui.add_game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.databinding.ActivityAddGameBinding;
import br.com.vicentec12.mygames.util.InstantiateUtil;
import br.com.vicentec12.mygames.util.ValidationUtil;

public class AddGameActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String EXTRA_GAME = "game";
    private static final String EXTRA_CONSOLE = "console";

    private ActivityAddGameBinding mBinding;

    private Game mSelectedGame;
    private Console mSelectedConsole;
    private AddGameViewModel mViewModel;

    public static Intent newIntentInstance(Context context, Game mGame, Console mConsole) {
        Intent mIntent = new Intent(context, AddGameActivity.class);
        mIntent.putExtra(EXTRA_GAME, mGame);
        mIntent.putExtra(EXTRA_CONSOLE, mConsole);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        mSelectedConsole = (Console) getIntent().getSerializableExtra(EXTRA_CONSOLE);
        setupViewModel();
        setupToolbar();
//        setupSpinnerConsole();
//        setupSpinnerConsoleSelected();
        setupMessage();
        setupEventDatabase();
//        mViewModel.listConsoles(mSelectedConsole);
    }

    private void setupViewModel() {
        AddGameViewModelFactory mFactory = new AddGameViewModelFactory(InstantiateUtil.initGameRepository(this),
                InstantiateUtil.initConsoleRepository(this));
        mViewModel = ViewModelProviders.of(this, mFactory).get(AddGameViewModel.class);
        mViewModel.setGame(mSelectedGame);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }

    private void setupToolbar() {
        setSupportActionBar(mBinding.lytToolbar.toolbar);
    }

    private void setupMessage() {
        mViewModel.getMutableMessage().observe(this, integerEvent -> {
            Integer mMessage = integerEvent.getContentIfNotHandled();
            if (mMessage != null)
                Snackbar.make(mBinding.tilAddGameName, getText(mMessage), BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
        });
    }

    private void setupEventDatabase() {
        mViewModel.getMutableEventDatabase().observe(this, booleanEvent -> {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
