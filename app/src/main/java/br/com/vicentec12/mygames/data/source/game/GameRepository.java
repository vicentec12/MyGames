package br.com.vicentec12.mygames.data.source.game;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.interfaces.Callbacks;

public class GameRepository implements GameDataSource {

    private static GameRepository INSTANCE = null;

    private final GameDataSource gameLocalDataSource;

    private GameRepository(@NonNull GameDataSource gameLocalDataSource) {
        this.gameLocalDataSource = gameLocalDataSource;
    }

    public static GameRepository getInstance(GameDataSource gameLocalDataSource) {
        if (INSTANCE == null)
            INSTANCE = new GameRepository(gameLocalDataSource);
        return INSTANCE;
    }

    @Override
    public void list(int sortBy, OnGamesListedCallback callback) {
        gameLocalDataSource.list(sortBy, callback);
    }

    @Override
    public void get(int id, OnGameGetedCallback callback) {
        gameLocalDataSource.get(id, callback);
    }

    @Override
    public void insert(@NonNull Game game, Callbacks.OnLocalCallback callback) {
        gameLocalDataSource.insert(game, callback);
    }

    @Override
    public void update(@NonNull Game game, Callbacks.OnLocalCallback callback) {
        gameLocalDataSource.update(game, callback);
    }

    @Override
    public void delete(@NonNull List<Game> games, OnGameDeletedCallback callback) {
        gameLocalDataSource.delete(games, callback);
    }

    @Override
    public void deleteAll() {
        gameLocalDataSource.deleteAll();
    }

}
