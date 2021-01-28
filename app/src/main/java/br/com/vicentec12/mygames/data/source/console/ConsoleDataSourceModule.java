package br.com.vicentec12.mygames.data.source.console;

import javax.inject.Singleton;

import br.com.vicentec12.mygames.data.source.Local;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ConsoleDataSourceModule {

    @Binds
    @Local
    @Singleton
    abstract ConsoleDataSource bindConsoleLocalDataSource(ConsoleLocalDataSource mConsoleLocalDataSource);

    @Binds
    @Singleton
    abstract ConsoleDataSource bindConsoleRepository(ConsoleRepository mConsoleRepository);

}
