package br.com.vicentec12.mygames.di;

import android.content.Context;

import javax.inject.Singleton;

import br.com.vicentec12.mygames.data.source.DataModule;
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSourceModule;
import br.com.vicentec12.mygames.data.source.game.GameDataSourceModule;
import br.com.vicentec12.mygames.ui.add_game.di.AddGameComponent;
import br.com.vicentec12.mygames.ui.console.di.ConsoleComponent;
import br.com.vicentec12.mygames.ui.game.di.GameComponent;
import br.com.vicentec12.mygames.ui.splash.di.SplashComponent;
import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        DataModule.class,
        ConsoleDataSourceModule.class,
        GameDataSourceModule.class,
        SubComponentsModule.class,
        ViewModelModule.class
})
public interface AppComponent {

    @Component.Factory
    interface Factory {

        AppComponent create(@BindsInstance Context mApplicationContext);

    }

    AddGameComponent.Factory addGameComponent();

    ConsoleComponent.Factory consoleComponent();

    GameComponent.Factory gameComponent();

    SplashComponent.Factory splashComponent();

}
