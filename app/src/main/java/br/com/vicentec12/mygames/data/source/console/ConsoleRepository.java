package br.com.vicentec12.mygames.data.source.console;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.interfaces.Callbacks;

public class ConsoleRepository implements ConsoleDataSource {

    private static ConsoleRepository INSTANCE = null;

    private final ConsoleDataSource consoleLocalDataSource;

    private ConsoleRepository(@NonNull ConsoleDataSource consoleLocalDataSource) {
        this.consoleLocalDataSource = consoleLocalDataSource;
    }

    public static ConsoleRepository getInstance(ConsoleDataSource consoleLocalDataSource) {
        if (INSTANCE == null)
            INSTANCE = new ConsoleRepository(consoleLocalDataSource);
        return INSTANCE;
    }

    @Override
    public void listConsolesWithGames(OnConsolesListedCallback callback) {
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
