package br.com.vicentec12.mygames.data.source.game;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.interfaces.Callbacks;
import br.com.vicentec12.mygames.util.AppExecutors;

@Singleton
public class GameLocalDataSource implements GameDataSource {

    public static final int ORDER_BY_NAME = 0;
    public static final int ORDER_BY_YEAR = 1;

    private final GameDao mGameDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public GameLocalDataSource(@NonNull GameDao mGameDao, @NonNull AppExecutors mAppExecutors) {
        this.mGameDao = mGameDao;
        this.mAppExecutors = mAppExecutors;
    }

    @Override
    public void list(@NonNull Console console, int sortBy, OnGamesListedCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            List<Game> games;
            if (sortBy == ORDER_BY_NAME)
                games = mGameDao.list(console.getId());
            else
                games = mGameDao.listByYear(console.getId());
            mAppExecutors.mainThread().execute(() -> {
                if (games.isEmpty())
                    callback.onFailure();
                else
                    callback.onSuccess(games);
            });
        });
    }

    @Override
    public void get(int id, OnGameGetedCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            Game game = mGameDao.get(id);
            mAppExecutors.mainThread().execute(() -> {
                if (game == null)
                    callback.onFailure();
                else
                    callback.onSuccess(game);
            });
        });
    }

    @Override
    public void insert(@NonNull Game game, Callbacks.OnLocalCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            long rowIds = mGameDao.insert(game);
            mAppExecutors.mainThread().execute(() -> {
                if (rowIds > 0)
                    callback.onSuccess(R.string.message_game_inserted);
                else
                    callback.onFailure(R.string.error_message_game_insert);
            });
        });
    }

    @Override
    public void update(@NonNull Game game, Callbacks.OnLocalCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            int numUpdated = mGameDao.update(game);
            mAppExecutors.mainThread().execute(() -> {
                if (numUpdated > 0)
                    callback.onSuccess(R.string.message_game_updated);
                else
                    callback.onFailure(R.string.error_message_game_update);
            });
        });
    }

    @Override
    public void delete(@NonNull List<Game> games, OnGameDeletedCallback callback) {
        mAppExecutors.diskIO().execute(() -> {
            int numDeleted = mGameDao.delete(games);
            mAppExecutors.mainThread().execute(() -> {
                if (numDeleted > 0)
                    callback.onSuccess(R.plurals.plural_message_games_deleted, numDeleted);
                else
                    callback.onFailure(R.string.error_message_game_delete);
            });
        });
    }

    @Override
    public void deleteAll() {
        mAppExecutors.diskIO().execute(mGameDao::deleteAll);
    }

}
