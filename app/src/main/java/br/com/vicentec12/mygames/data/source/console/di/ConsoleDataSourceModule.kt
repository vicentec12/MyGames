package br.com.vicentec12.mygames.data.source.console.di

import br.com.vicentec12.mygames.data.source.AppDatabase
import br.com.vicentec12.mygames.data.source.di.Local
import br.com.vicentec12.mygames.data.source.console.ConsoleDao
import br.com.vicentec12.mygames.data.source.console.ConsoleDataSource
import br.com.vicentec12.mygames.data.source.console.ConsoleLocalDataSource
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConsoleDataSourceModule {

    @Binds
    @Local
    @Singleton
    abstract fun bindsConsoleLocalDataSource(mDataSource: ConsoleLocalDataSource): ConsoleDataSource

    @Binds
    @Singleton
    abstract fun bindsConsoleRepository(mRepository: ConsoleRepository): ConsoleDataSource

    companion object {

        @Provides
        @Singleton
        fun provideConsoleDao(appDatabase: AppDatabase): ConsoleDao {
            return appDatabase.getConsoleDao()
        }

    }

}