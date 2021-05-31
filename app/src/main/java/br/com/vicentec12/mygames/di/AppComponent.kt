package br.com.vicentec12.mygames.di

import android.content.Context
import br.com.vicentec12.mygames.data.source.DataModule
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSourceModule
import br.com.vicentec12.mygames.data.source.game.GameDataSourceModule
import br.com.vicentec12.mygames.ui.add_game.di.AddGameComponent
import br.com.vicentec12.mygames.ui.console.di.ConsoleComponent
import br.com.vicentec12.mygames.ui.game.di.GameComponent
import br.com.vicentec12.mygames.ui.splash.di.SplashComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DataModule::class,
    ConsoleDataSourceModule::class,
    GameDataSourceModule::class,
    ViewModelProviderFactoryModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance mApplicationContext: Context): AppComponent

    }

    fun addGameComponent(): AddGameComponent.Factory

    fun consoleComponent(): ConsoleComponent.Factory

    fun gameComponent(): GameComponent.Factory

    fun splashComponent(): SplashComponent.Factory

}