package br.com.vicentec12.mygames.data.source.game

import br.com.vicentec12.mygames.data.source.AppDatabase
import br.com.vicentec12.mygames.data.source.Local
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class GameDataSourceModule {

    @Binds
    @Local
    @Singleton
    abstract fun bindsGameLocalDataSource(mLocalDataSource: GameLocalDataSource): GameDataSource

    @Binds
    @Singleton
    abstract fun bindsGameRepository(mRepository: GameRepository): GameDataSource

    @Module
    companion object {

        @Provides
        @Singleton
        fun providesGameDao(appDatabase: AppDatabase): GameDao {
            return appDatabase.getGameDao()
        }

    }

}