package br.com.vicentec12.mygames.data.source.game;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.Local;
import br.com.vicentec12.mygames.interfaces.Callbacks;

@Singleton
public class GameRepository implements GameDataSource {

    private final GameDataSource gameLocalDataSource;

    @Inject
    public GameRepository(@Local GameDataSource gameLocalDataSource) {
        this.gameLocalDataSource = gameLocalDataSource;
    }

    @Override
    public void list(@NonNull Console console, int sortBy, OnGamesListedCallback callback) {
        gameLocalDataSource.list(console, sortBy, callback);
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
