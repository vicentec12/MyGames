package br.com.vicentec12.mygames.data.source.game.di

import br.com.vicentec12.mygames.data.source.AppDatabase
import br.com.vicentec12.mygames.data.source.di.Local
import br.com.vicentec12.mygames.data.source.game.GameDao
import br.com.vicentec12.mygames.data.source.game.GameDataSource
import br.com.vicentec12.mygames.data.source.game.GameLocalDataSource
import br.com.vicentec12.mygames.data.source.game.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameDataSourceModule {

    @Binds
    @Local
    @Singleton
    abstract fun bindsGameLocalDataSource(mLocalDataSource: GameLocalDataSource): GameDataSource

    @Binds
    @Singleton
    abstract fun bindsGameRepository(mRepository: GameRepository): GameDataSource

    companion object {

        @Provides
        @Singleton
        fun providesGameDao(appDatabase: AppDatabase): GameDao {
            return appDatabase.getGameDao()
        }

    }

}