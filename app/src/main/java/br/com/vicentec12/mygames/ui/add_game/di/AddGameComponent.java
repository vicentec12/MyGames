package br.com.vicentec12.mygames.ui.add_game.di;

import br.com.vicentec12.mygames.ui.add_game.AddGameActivity;
import dagger.Subcomponent;

@Subcomponent
public interface AddGameComponent {

    @Subcomponent.Factory
    interface Factory {

        AddGameComponent create();

    }

    void inject(AddGameActivity mAddGameActivity);

}
