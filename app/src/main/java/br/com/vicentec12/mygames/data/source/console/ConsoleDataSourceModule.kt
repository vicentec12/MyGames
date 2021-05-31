package br.com.vicentec12.mygames.data.source.console

import br.com.vicentec12.mygames.data.source.AppDatabase
import br.com.vicentec12.mygames.data.source.Local
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ConsoleDataSourceModule {

    @Binds
    @Local
    @Singleton
    abstract fun bindsConsoleLocalDataSource(mDataSource: ConsoleLocalDataSource): ConsoleDataSource

    @Binds
    @Singleton
    abstract fun bindsConsoleRepository(mRepository: ConsoleRepository): ConsoleDataSource

    @Module
    companion object {

        @Provides
        @Singleton
        fun provideConsoleDao(appDatabase: AppDatabase): ConsoleDao {
            return appDatabase.getConsoleDao()
        }

    }

}