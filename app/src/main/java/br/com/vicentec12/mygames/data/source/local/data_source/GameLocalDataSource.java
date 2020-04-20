package br.com.vicentec12.mygames.data.source.local.data_source;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.Callbacks;
import br.com.vicentec12.mygames.data.source.GameDataSource;
import br.com.vicentec12.mygames.data.source.local.AppDatabase;
import br.com.vicentec12.mygames.util.AppExecutors;

public class GameLocalDataSource implements GameDataSource {

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
    public void list(OnGamesListedCallback callback) {
        Runnable list = () -> {
            List<Game> games = appDatabase.getGameDao().list();
            appExecutors.mainThread().execute(() -> {
                if (games.isEmpty())
                    callback.onFailure();
                else
                    callback.onSuccess(games);
            });
        };
        appExecutors.diskIO().execute(list);
    }

    @Override
    public void get(int id, OnGameGetedCallback callback) {
        Runnable get = () -> {
            Game game = appDatabase.getGameDao().get(id);
            appExecutors.mainThread().execute(() -> {
                if (game == null)
                    callback.onFailure();
                else
                    callback.onSuccess(game);
            });
        };
        appExecutors.diskIO().execute(get);
    }

    @Override
    public void insert(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback) {
        Runnable insert = () -> {
            long rowIds = appDatabase.getGameDao().insert(game);
            appExecutors.mainThread().execute(() -> {
                if (rowIds > 0)
                    callback.onSuccess(context.getString(R.string.message_game_inserted));
                else
                    callback.onFailure(context.getString(R.string.error_message_game_insert));
            });
        };
        appExecutors.diskIO().execute(insert);
    }

    @Override
    public void update(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback) {
        Runnable update = () -> {
            int numUpdated = appDatabase.getGameDao().update(game);
            appExecutors.mainThread().execute(() -> {
                if (numUpdated > 0)
                    callback.onSuccess(context.getString(R.string.message_game_updated));
                else
                    callback.onFailure(context.getString(R.string.error_message_game_update));
            });
        };
        appExecutors.diskIO().execute(update);
    }

    @Override
    public void delete(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback) {
        Runnable delete = () -> {
            int numDeleted = appDatabase.getGameDao().delete(game);
            appExecutors.mainThread().execute(() -> {
                if (numDeleted > 0)
                    callback.onSuccess(context.getString(R.string.message_game_deleted));
                else
                    callback.onFailure(context.getString(R.string.error_message_game_delete));
            });
        };
        appExecutors.diskIO().execute(delete);
    }

    @Override
    public void delete(Context context, @NonNull List<Game> games, Callbacks.OnLocalCallback callback) {
        Runnable delete = () -> {
            int numDeleted = appDatabase.getGameDao().delete(games);
            appExecutors.mainThread().execute(() -> {
                if (numDeleted > 0)
                    callback.onSuccess(context.getResources().getQuantityString(R.plurals.message_games_deleted, numDeleted, numDeleted));
                else
                    callback.onFailure(context.getString(R.string.error_message_game_delete));
            });
        };
        appExecutors.diskIO().execute(delete);
    }


    @Override
    public void deleteAll() {
        appExecutors.diskIO().execute(() -> appDatabase.getGameDao().deleteAll());
    }

}
