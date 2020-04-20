package br.com.vicentec12.mygames.data.source;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Game;

public interface GameDataSource {

    interface OnGamesListedCallback {

        void onSuccess(List<Game> games);

        void onFailure();

    }

    interface OnGameGetedCallback {

        void onSuccess(Game game);

        void onFailure();

    }

    void list(OnGamesListedCallback callback);

    void get(int id, OnGameGetedCallback callback);

    void insert(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback);

    void update(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback);

    void delete(Context context, @NonNull Game game, Callbacks.OnLocalCallback callback);

    void delete(Context context, @NonNull List<Game> games, Callbacks.OnLocalCallback callback);

    void deleteAll();

}
