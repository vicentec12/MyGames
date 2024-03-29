package br.com.vicentec12.mygames.di

import br.com.vicentec12.mygames.data.local.AppDatabase
import br.com.vicentec12.mygames.data.local.source.GameLocalDataSource
import br.com.vicentec12.mygames.data.repository.GameDataRepository
import br.com.vicentec12.mygames.data.source.GameDataSource
import br.com.vicentec12.mygames.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameDataModule {

    @Binds
    @Local
    @Singleton
    abstract fun bindsGameLocalDataSource(mLocalDataSource: GameLocalDataSource): GameDataSource

    @Binds
    @Singleton
    abstract fun bindsGameRepository(mRepository: GameDataRepository): GameRepository

    companion object {
        @Provides
        @Singleton
        fun providesGameDao(appDatabase: AppDatabase) = appDatabase.getGameDao()
    }

}