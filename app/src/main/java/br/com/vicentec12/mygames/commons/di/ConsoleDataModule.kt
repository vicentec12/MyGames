package br.com.vicentec12.mygames.commons.di

import br.com.vicentec12.mygames.data.local.AppDatabase
import br.com.vicentec12.mygames.data.local.source.ConsoleLocalDataSource
import br.com.vicentec12.mygames.data.repository.ConsoleRepositoryImpl
import br.com.vicentec12.mygames.data.source.ConsoleDataSource
import br.com.vicentec12.mygames.data.repository.ConsoleRepository
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
    abstract fun bindsConsoleRepository(mRepository: ConsoleRepositoryImpl): ConsoleRepository

    companion object {
        @Provides
        @Singleton
        fun provideConsoleDao(appDatabase: AppDatabase) = appDatabase.getConsoleDao()
    }
}