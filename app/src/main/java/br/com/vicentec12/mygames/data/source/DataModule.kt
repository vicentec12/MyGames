package br.com.vicentec12.mygames.data.source

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Provides
    @Singleton
    fun providesAppDatabase(mContext: Context): AppDatabase {
        return Room.databaseBuilder(mContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .build()
    }

}