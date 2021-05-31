package br.com.vicentec12.mygames.di

import br.com.vicentec12.mygames.ui.add_game.di.AddGameComponent
import br.com.vicentec12.mygames.ui.console.di.ConsoleComponent
import br.com.vicentec12.mygames.ui.game.di.GameComponent
import br.com.vicentec12.mygames.ui.splash.di.SplashComponent
import dagger.Module

@Module(
        subcomponents = [
            AddGameComponent::class,
            ConsoleComponent::class,
            GameComponent::class,
            SplashComponent::class
        ]
)
abstract class AppModule