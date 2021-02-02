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

    private final ConsoleDao mConsoleDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public ConsoleLocalDataSource(@NonNull ConsoleDao mConsoleDao, @NonNull AppExecutors mAppExecutors) {
        this.mConsoleDao = mConsoleDao;
        this.mAppExecutors = mAppExecutors;
    }

    @Override
    public void listConsoles(OnConsolesListedCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            List<Console> consoles = mConsoleDao.list();
            mAppExecutors.mainThread().execute(() -> {
                if (consoles.size() > 0)
                    callback.onSucess(R.string.message_consoles_listed, consoles);
                else
                    callback.onErro(R.string.error_message_consoles_listed);
            });
        });
    }

    @Override
    public void listConsolesWithGames(OnConsolesWithGamesListedCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            List<ConsoleWithGames> consolesWithGames = mConsoleDao.listConsolesWithGames();
            mAppExecutors.mainThread().execute(() -> {
                if (consolesWithGames.size() > 0)
                    callback.onSucess(R.string.message_consoles_listed, consolesWithGames);
                else
                    callback.onErro(R.string.error_message_consoles_listed);
            });
        });
    }

    @Override
    public void insertConsoles(List<Console> consoles, Callbacks.OnLocalCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            mConsoleDao.insert(consoles);
            mAppExecutors.mainThread().execute(() -> callback.onSuccess(R.string.message_consoles_inserted));
        });
    }

    @Override
    public void deleteConsole(Console console, Callbacks.OnLocalCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            int result = mConsoleDao.delete(console);
            mAppExecutors.mainThread().execute(() -> {
                if (result > 0)
                    callback.onSuccess(R.string.message_console_deleted);
                else
                    callback.onFailure(R.string.error_message_console_delete);
            });
        });
    }

    @Override
    public void deleteAllConsoles(Callbacks.OnLocalCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            mConsoleDao.deleteAll();
            mAppExecutors.mainThread().execute(() -> callback.onSuccess(R.string.message_console_deleted));
        });
    }

}
