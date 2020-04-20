package br.com.vicentec12.mygames.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        init();
    }

    private void init() {
        _tilName = findViewById(R.id.til_add_game_name);
        _tilYear = findViewById(R.id.til_add_game_year);
        config();
    }

    private void config() {

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

    public void saveGame(View view) {
        if (validateFields()) {
            String name = _tilName.getEditText().getText().toString();
            String year = _tilYear.getEditText().getText().toString();
            Game game = new Game(name, year);
            GameRepository mGameRepository = InstantiateUtil.instantialeGameRepository(this);
            mGameRepository.insert(this, game, new Callbacks.OnLocalCallback() {
                @Override
                public void onSuccess(String message) {
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
    }

}
