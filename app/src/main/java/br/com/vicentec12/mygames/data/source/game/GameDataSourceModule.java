package br.com.vicentec12.mygames.data.source.game;

import javax.inject.Singleton;

import br.com.vicentec12.mygames.data.source.AppDatabase;
import br.com.vicentec12.mygames.data.source.Local;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class GameDataSourceModule {

    @Binds
    @Local
    @Singleton
    abstract GameDataSource bindGameLocalDataSource(GameLocalDataSource mGameLocalDataSource);

    @Binds
    @Singleton
    abstract GameDataSource bindGameRepository(GameRepository mGameRepository);

    @Provides
    @Singleton
    static GameDao provideGameDao(AppDatabase mAppDatabase) {
        return mAppDatabase.getGameDao();
    }

}
