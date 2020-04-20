package br.com.vicentec12.mygames.data.source.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.Callbacks;
import br.com.vicentec12.mygames.data.source.GameDataSource;

import static com.google.android.gms.internal.zzdog.checkNotNull;

public class GameRepository implements GameDataSource {

    private static GameRepository INSTANCE = null;

    private final GameDataSource gameLocalDataSource;

    private GameRepository(@NonNull GameDataSource gameLocalDataSource) {
        this.gameLocalDataSource = checkNotNull(gameLocalDataSource);
    }

    public static GameRepository getInstance(GameDataSource gameLocalDataSource) {
        if (INSTANCE == null)
            INSTANCE = new GameRepository(gameLocalDataSource);
        return INSTANCE;
    }

    @Override
    public void list(OnGamesListedCallback callback) {
        gameLocalDataSource.list(callback);
    }

    @Override
    public void get(int id, OnGameGetedCallback callback) {
        gameLocalDataSource.get(id, callback);
    }

    @Override
    public void insert(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback) {
        gameLocalDataSource.insert(context, game, callback);
    }

    @Override
    public void update(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback) {
        gameLocalDataSource.update(context, game, callback);
    }

    @Override
    public void delete(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback) {
        gameLocalDataSource.delete(context, game, callback);
    }

    @Override
    public void delete(Context context, @NonNull List<Game> games, Callbacks.OnLocalCallback callback) {
        gameLocalDataSource.delete(context, games, callback);
    }

    @Override
    public void deleteAll() {
        gameLocalDataSource.deleteAll();
    }
}
