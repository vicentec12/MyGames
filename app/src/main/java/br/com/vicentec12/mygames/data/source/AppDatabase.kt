package br.com.vicentec12.mygames.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.data.source.console.ConsoleDao
import br.com.vicentec12.mygames.data.source.game.GameDao

@Database(entities = [Console::class, Game::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getConsoleDao(): ConsoleDao

    abstract fun getGameDao(): GameDao

    companion object {

        const val DATABASE_NAME = "mygames.db"

    }

}