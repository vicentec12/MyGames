package br.com.vicentec12.mygames.ui.console.di;

import br.com.vicentec12.mygames.ui.console.ConsoleActivity;
import dagger.Subcomponent;

@Subcomponent
public interface ConsoleComponent {

    @Subcomponent.Factory
    interface Factory {

        ConsoleComponent create();

    }

    void inject(ConsoleActivity mConsoleActivity);

}
