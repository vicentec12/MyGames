package br.com.vicentec12.mygames.di

import br.com.vicentec12.mygames.data.local.AppDatabase
import br.com.vicentec12.mygames.data.local.source.ConsoleLocalDataSource
import br.com.vicentec12.mygames.data.repository.ConsoleDataRepository
import br.com.vicentec12.mygames.data.source.ConsoleDataSource
import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConsoleDataModule {

    @Binds
    @Local
    @Singleton
    abstract fun bindsConsoleLocalDataSource(mDataSource: ConsoleLocalDataSource): ConsoleDataSource

    @Binds
    @Singleton
    abstract fun bindsConsoleRepository(mRepository: ConsoleDataRepository): ConsoleRepository

    companion object {
        @Provides
        @Singleton
        fun provideConsoleDao(appDatabase: AppDatabase) = appDatabase.getConsoleDao()
    }

}