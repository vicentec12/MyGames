package br.com.vicentec12.mygames.data.source.game;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.AppDatabase;
import br.com.vicentec12.mygames.extensions.AppExecutors;
import br.com.vicentec12.mygames.interfaces.Callbacks;

public class GameLocalDataSource implements GameDataSource {

    public static final int ORDER_BY_NAME = 0;
    public static final int ORDER_BY_YEAR = 1;

    private static volatile GameLocalDataSource INSTANCE;

    private AppDatabase appDatabase;
    private AppExecutors appExecutors;

    private GameLocalDataSource(@NonNull AppDatabase appDatabase, @NonNull AppExecutors appExecutors) {
        this.appDatabase = appDatabase;
        this.appExecutors = appExecutors;
    }

    public static GameLocalDataSource getInstance(@NonNull AppDatabase appDatabase, @NonNull AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (GameLocalDataSource.class) {
                if (INSTANCE == null)
                    INSTANCE = new GameLocalDataSource(appDatabase, appExecutors);
            }
        }
        return INSTANCE;
    }

    @Override
    public void list(@NonNull Console console, int sortBy, OnGamesListedCallback callback) {
        appExecutors.diskIO().execute(() -> {
            List<Game> games;
            if (sortBy == ORDER_BY_NAME)
                games = appDatabase.getGameDao().list(console.getId());
            else
                games = appDatabase.getGameDao().listByYear(console.getId());
            appExecutors.mainThread().execute(() -> {
                if (games.isEmpty())
                    callback.onFailure();
                else
                    callback.onSuccess(games);
            });
        });
    }

    @Override
    public void get(int id, OnGameGetedCallback callback) {
        appExecutors.diskIO().execute(() -> {
            Game game = appDatabase.getGameDao().get(id);
            appExecutors.mainThread().execute(() -> {
                if (game == null)
                    callback.onFailure();
                else
                    callback.onSuccess(game);
            });
        });
    }

    @Override
    public void insert(@NonNull Game game, Callbacks.OnLocalCallback callback) {
        appExecutors.diskIO().execute(() -> {
            long rowIds = appDatabase.getGameDao().insert(game);
            appExecutors.mainThread().execute(() -> {
                if (rowIds > 0)
                    callback.onSuccess(R.string.message_game_inserted);
                else
                    callback.onFailure(R.string.error_message_game_insert);
            });
        });
    }

    @Override
    public void update(@NonNull Game game, Callbacks.OnLocalCallback callback) {
        appExecutors.diskIO().execute(() -> {
            int numUpdated = appDatabase.getGameDao().update(game);
            appExecutors.mainThread().execute(() -> {
                if (numUpdated > 0)
                    callback.onSuccess(R.string.message_game_updated);
                else
                    callback.onFailure(R.string.error_message_game_update);
            });
        });
    }

    @Override
    public void delete(@NonNull List<Game> games, OnGameDeletedCallback callback) {
        appExecutors.diskIO().execute(() -> {
            int numDeleted = appDatabase.getGameDao().delete(games);
            appExecutors.mainThread().execute(() -> {
                if (numDeleted > 0)
                    callback.onSuccess(R.plurals.plural_message_games_deleted, numDeleted);
                else
                    callback.onFailure(R.string.error_message_game_delete);
            });
        });
    }

    @Override
    public void deleteAll() {
        appExecutors.diskIO().execute(() -> appDatabase.getGameDao().deleteAll());
    }

}
