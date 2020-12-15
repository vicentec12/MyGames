package br.com.vicentec12.mygames.data.source.game;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.interfaces.Callbacks;

public interface GameDataSource {

    interface OnGamesListedCallback {

        void onSuccess(List<Game> games);

        void onFailure();

    }

    interface OnGameGetedCallback {

        void onSuccess(Game game);

        void onFailure();

    }


    interface OnGameDeletedCallback {

        void onSuccess(int mMessage, int mNumDeleted);

        void onFailure(int mMessage);

    }

    void list(int sortBy, OnGamesListedCallback callback);

    void get(int id, OnGameGetedCallback callback);

    void insert(@NonNull Game game, Callbacks.OnLocalCallback callback);

    void update(@NonNull Game game, Callbacks.OnLocalCallback callback);

    void delete(@NonNull List<Game> games, OnGameDeletedCallback callback);

    void deleteAll();

}
