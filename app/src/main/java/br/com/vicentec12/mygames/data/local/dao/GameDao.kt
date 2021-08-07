package br.com.vicentec12.mygames.data.local.dao

import androidx.room.*
import br.com.vicentec12.mygames.data.local.entities.GameEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM game WHERE console_id = :mIdConsole ORDER BY name")
    suspend fun list(mIdConsole: Long): List<GameEntity>?

    @Query("SELECT * FROM game WHERE console_id = :mIdConsole ORDER BY year")
    suspend fun listByYear(mIdConsole: Long): List<GameEntity>?

    @Query("SELECT * FROM game WHERE id = :mId")
    suspend fun get(mId: Long): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mGame: GameEntity?): Long

    @Update
    suspend fun update(mGame: GameEntity?): Int

    @Delete
    suspend fun delete(mGames: List<GameEntity?>?): Int

    @Query("DELETE FROM game")
    suspend fun deleteAll()

}