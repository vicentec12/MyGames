package br.com.vicentec12.mygames.data.source.console;

import javax.inject.Singleton;

import br.com.vicentec12.mygames.data.source.AppDatabase;
import br.com.vicentec12.mygames.data.source.Local;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ConsoleDataSourceModule {

    @Binds
    @Local
    @Singleton
    abstract ConsoleDataSource bindConsoleLocalDataSource(ConsoleLocalDataSource mConsoleLocalDataSource);

    @Binds
    @Singleton
    abstract ConsoleDataSource bindConsoleRepository(ConsoleRepository mConsoleRepository);

    @Provides
    @Singleton
    static ConsoleDao provideConsoleDao(AppDatabase mAppDatabase) {
        return mAppDatabase.getConsoleDao();
    }

}
