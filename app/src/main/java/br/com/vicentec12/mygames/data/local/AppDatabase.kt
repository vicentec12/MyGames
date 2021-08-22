package br.com.vicentec12.mygames.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.vicentec12.mygames.data.local.entities.ConsoleEntity
import br.com.vicentec12.mygames.data.local.entities.GameEntity
import br.com.vicentec12.mygames.data.local.dao.ConsoleDao
import br.com.vicentec12.mygames.data.local.dao.GameDao

@Database(entities = [ConsoleEntity::class, GameEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getConsoleDao(): ConsoleDao

    abstract fun getGameDao(): GameDao

    companion object {

        const val DATABASE_NAME = "mygames.db"

    }

}