package br.com.vicentec12.mygames.ui.game.di;

import br.com.vicentec12.mygames.ui.game.GameActivity;
import dagger.Subcomponent;

@Subcomponent
public interface GameComponent {

    @Subcomponent.Factory
    interface Factory {

        GameComponent create();

    }

    void inject(GameActivity mGameActivity);

}
