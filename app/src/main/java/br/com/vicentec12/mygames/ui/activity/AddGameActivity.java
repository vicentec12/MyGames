package br.com.vicentec12.mygames.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.Callbacks;
import br.com.vicentec12.mygames.data.source.repository.GameRepository;
import br.com.vicentec12.mygames.util.InstantiateUtil;
import br.com.vicentec12.mygames.util.ValidationUtil;

public class AddGameActivity extends AppCompatActivity {

    private TextInputLayout _tilName;
    private TextInputLayout _tilYear;
    private FloatingActionButton _fabAddGame;

    private boolean isUpdate;
    private Game mSelectedGame;
    private GameRepository mGameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        init();
    }

    private void init() {
        _tilName = findViewById(R.id.til_add_game_name);
        _tilYear = findViewById(R.id.til_add_game_year);
        _fabAddGame = findViewById(R.id.fab_add_game);
        config();
    }

    private void config() {
        configUpdate();
    }

    private void configUpdate() {
        mSelectedGame = (Game) getIntent().getSerializableExtra("game");
        if (mSelectedGame != null) {
            isUpdate = true;
            _tilName.getEditText().setText(mSelectedGame.getName());
            _tilYear.getEditText().setText(mSelectedGame.getYear());
            _fabAddGame.setImageResource(R.drawable.ic_edit);
        }
    }

    private boolean validateFields() {
        removeFieldErrors();
        if (!ValidationUtil.validateEmptyField(this, _tilName))
            return false;
        if (!ValidationUtil.validateEmptyField(this, _tilYear))
            return false;
        return true;
    }

    private void removeFieldErrors() {
        ValidationUtil.removeErrorTextInputLayout(_tilName);
        ValidationUtil.removeErrorTextInputLayout(_tilYear);
    }

    public void fabEvent(View view) {
        if (validateFields()) {
            String name = _tilName.getEditText().getText().toString();
            String year = _tilYear.getEditText().getText().toString();
            mGameRepository = InstantiateUtil.instantialeGameRepository(this);
            if (isUpdate) {
                mSelectedGame.setName(name);
                mSelectedGame.setYear(year);
                updateGame(mSelectedGame);
            } else {
                Game game = new Game(name, year);
                insertGame(game);
            }
        }
    }

    private void insertGame(Game game) {
        GameRepository mGameRepository = InstantiateUtil.instantialeGameRepository(this);
        mGameRepository.insert(this, game, new Callbacks.OnLocalCallback() {
            @Override
            public void onSuccess(String message) {
                setResult(RESULT_OK);
                _tilName.getEditText().setText("");
                _tilYear.getEditText().setText("");
                Snackbar.make(_tilName, message, BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(_tilName, message, BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            }
        });
    }

    private void updateGame(Game game) {
        mGameRepository.update(this, game, new Callbacks.OnLocalCallback() {
            @Override
            public void onSuccess(String message) {
                setResult(RESULT_OK);
                Snackbar.make(_tilName, message, BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(_tilName, message, BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            }
        });
    }

}
