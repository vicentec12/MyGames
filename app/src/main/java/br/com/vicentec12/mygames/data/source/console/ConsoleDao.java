package br.com.vicentec12.mygames.data.source.console;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.ConsoleWithGames;

@Dao
public interface ConsoleDao {

    @Query("SELECT * FROM console")
    List<Console> list();

    @Transaction
    @Query("SELECT * FROM console")
    List<ConsoleWithGames> listConsolesWithGames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Console> consoles);

    @Delete
    int delete(Console console);

    @Query("DELETE FROM console")
    void deleteAll();

}
