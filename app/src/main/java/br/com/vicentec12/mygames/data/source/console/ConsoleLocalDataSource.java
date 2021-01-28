package br.com.vicentec12.mygames.data.source.console;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.data.source.AppDatabase;
import br.com.vicentec12.mygames.interfaces.Callbacks;
import br.com.vicentec12.mygames.util.AppExecutors;

@Singleton
public class ConsoleLocalDataSource implements ConsoleDataSource {

    private final AppDatabase appDatabase;
    private final AppExecutors appExecutors;

    @Inject
    public ConsoleLocalDataSource(@NonNull AppDatabase appDatabase, @NonNull AppExecutors appExecutors) {
        this.appDatabase = appDatabase;
        this.appExecutors = appExecutors;
    }

    @Override
    public void listConsoles(OnConsolesListedCallback callback) {
        appExecutors.diskIO().execute(() -> {
            List<Console> consoles = appDatabase.getConsoleDao().list();
            appExecutors.mainThread().execute(() -> {
                if (consoles.size() > 0)
                    callback.onSucess(R.string.message_consoles_listed, consoles);
                else
                    callback.onErro(R.string.error_message_consoles_listed);
            });
        });
    }

    @Override
    public void listConsolesWithGames(OnConsolesWithGamesListedCallback callback) {
        appExecutors.diskIO().execute(() -> {
            List<ConsoleWithGames> consolesWithGames = appDatabase.getConsoleDao().listConsolesWithGames();
            appExecutors.mainThread().execute(() -> {
                if (consolesWithGames.size() > 0)
                    callback.onSucess(R.string.message_consoles_listed, consolesWithGames);
                else
                    callback.onErro(R.string.error_message_consoles_listed);
            });
        });
    }

    @Override
    public void insertConsoles(List<Console> consoles, Callbacks.OnLocalCallback callback) {
        appExecutors.diskIO().execute(() -> {
            appDatabase.getConsoleDao().insert(consoles);
            appExecutors.mainThread().execute(() -> callback.onSuccess(R.string.message_consoles_inserted));
        });
    }

    @Override
    public void deleteConsole(Console console, Callbacks.OnLocalCallback callback) {
        appExecutors.diskIO().execute(() -> {
            int result = appDatabase.getConsoleDao().delete(console);
            appExecutors.mainThread().execute(() -> {
                if (result > 0)
                    callback.onSuccess(R.string.message_console_deleted);
                else
                    callback.onFailure(R.string.error_message_console_delete);
            });
        });
    }

    @Override
    public void deleteAllConsoles(Callbacks.OnLocalCallback callback) {
        appExecutors.diskIO().execute(() -> {
            appDatabase.getConsoleDao().deleteAll();
            appExecutors.mainThread().execute(() -> callback.onSuccess(R.string.message_console_deleted));
        });
    }

}