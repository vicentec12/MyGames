package br.com.vicentec12.mygames.ui.add_game;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.game.GameRepository;
import br.com.vicentec12.mygames.databinding.ActivityAddGameBinding;
import br.com.vicentec12.mygames.util.InstantiateUtil;
import br.com.vicentec12.mygames.util.ValidationUtil;

public class AddGameActivity extends AppCompatActivity {

    private ActivityAddGameBinding mBinding;

    private Game mSelectedGame;
    private AddGameViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddGameBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    private void init() {
        mSelectedGame = (Game) getIntent().getSerializableExtra("game");
        setupViewModel();
        setupMessage();
        setupEventDatabase();
    }

    private void setupViewModel() {
        AddGameViewModel.AddGameViewModelFactory mFactory =
                new AddGameViewModel.AddGameViewModelFactory(InstantiateUtil.instantialeGameRepository(this));
        mViewModel = ViewModelProviders.of(this, mFactory).get(AddGameViewModel.class);
        mViewModel.setGame(mSelectedGame);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
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

}
