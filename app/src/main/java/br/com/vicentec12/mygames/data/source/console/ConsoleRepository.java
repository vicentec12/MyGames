package br.com.vicentec12.mygames.data.source.console;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.source.Local;
import br.com.vicentec12.mygames.interfaces.Callbacks;

@Singleton
public class ConsoleRepository implements ConsoleDataSource {

    private final ConsoleDataSource consoleLocalDataSource;

    @Inject
    public ConsoleRepository(@Local ConsoleDataSource consoleLocalDataSource) {
        this.consoleLocalDataSource = consoleLocalDataSource;
    }

    @Override
    public void listConsoles(OnConsolesListedCallback callback) {
        consoleLocalDataSource.listConsoles(callback);
    }

    @Override
    public void listConsolesWithGames(OnConsolesWithGamesListedCallback callback) {
        consoleLocalDataSource.listConsolesWithGames(callback);
    }

    @Override
    public void insertConsoles(List<Console> consoles, Callbacks.OnLocalCallback callback) {
        consoleLocalDataSource.insertConsoles(consoles, callback);
    }

    @Override
    public void deleteConsole(Console console, Callbacks.OnLocalCallback callback) {
        consoleLocalDataSource.deleteConsole(console, callback);
    }

    @Override
    public void deleteAllConsoles(Callbacks.OnLocalCallback callback) {
        consoleLocalDataSource.deleteAllConsoles(callback);
    }

}
