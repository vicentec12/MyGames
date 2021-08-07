package br.com.vicentec12.mygames.data.local.dao

import androidx.room.*
import br.com.vicentec12.mygames.data.local.entities.ConsoleEntity
import br.com.vicentec12.mygames.data.local.entities.ConsoleWithGamesEntity

@Dao
interface ConsoleDao {

    @Query("SELECT * FROM console")
    suspend fun list(): List<ConsoleEntity>

    @Transaction
    @Query("SELECT * FROM console")
    suspend fun listWithGames(): List<ConsoleWithGamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mConsoles: List<ConsoleEntity>)

    @Delete
    suspend fun delete(mConsole: ConsoleEntity): Int

    @Query("DELETE FROM console")
    suspend fun deleteAll()

}