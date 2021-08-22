package br.com.vicentec12.mygames.data.di

import android.app.Application
import androidx.room.Room
import br.com.vicentec12.mygames.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesAppDatabase(app: Application) = Room.databaseBuilder(
        app.applicationContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME
    ).build()

}