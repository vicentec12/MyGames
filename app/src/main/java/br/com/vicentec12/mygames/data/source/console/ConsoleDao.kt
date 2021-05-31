package br.com.vicentec12.mygames.data.source.console

import androidx.room.*
import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.model.ConsoleWithGames

@Dao
interface ConsoleDao {

    @Query("SELECT * FROM console")
    suspend fun list(): List<Console>

    @Transaction
    @Query("SELECT * FROM console")
    suspend fun listWithGames(): List<ConsoleWithGames>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mConsoles: List<Console>)

    @Delete
    suspend fun delete(mConsole: Console): Int

    @Query("DELETE FROM console")
    suspend fun deleteAll()

}