package br.com.vicentec12.mygames.data.source.game;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Game;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game ORDER BY name")
    List<Game> list();

    @Query("SELECT * FROM game WHERE _id = :id")
    Game get(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Game game);

    @Update
    int update(Game game);

    @Delete
    int delete(Game game);

    @Delete
    int delete(List<Game> games);

    @Query("DELETE FROM game")
    void deleteAll();

}
