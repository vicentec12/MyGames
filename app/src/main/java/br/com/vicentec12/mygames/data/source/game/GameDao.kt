package br.com.vicentec12.mygames.data.source.game

import androidx.room.*
import br.com.vicentec12.mygames.data.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM game WHERE console_id = :mIdConsole ORDER BY name")
    suspend fun list(mIdConsole: Long): List<Game?>?

    @Query("SELECT * FROM game WHERE console_id = :mIdConsole ORDER BY year")
    suspend fun listByYear(mIdConsole: Long): List<Game?>?

    @Query("SELECT * FROM game WHERE id = :mId")
    suspend fun get(mId: Long): Game?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mGame: Game?): Long

    @Update
    suspend fun update(mGame: Game?): Int

    @Delete
    suspend fun delete(mGames: List<Game?>?): Int

    @Query("DELETE FROM game")
    suspend fun deleteAll()

}