package br.com.vicentec12.mygames.data.source.game;

import javax.inject.Singleton;

import br.com.vicentec12.mygames.data.source.Local;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class GameDataSourceModule {

    @Binds
    @Local
    @Singleton
    abstract GameDataSource bindGameLocalDataSource(GameLocalDataSource mGameLocalDataSource);

    @Binds
    @Singleton
    abstract GameDataSource bindGameRepository(GameRepository mGameRepository);

}
