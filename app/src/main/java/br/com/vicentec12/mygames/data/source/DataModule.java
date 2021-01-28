package br.com.vicentec12.mygames.data.source;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static br.com.vicentec12.mygames.data.source.AppDatabase.MIGRATION_1_2;

@Module
public abstract class DataModule {

    @Singleton
    @Provides
    static AppDatabase provideLocalDatabase(Context mContext) {
        return Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_1_2).fallbackToDestructiveMigration().build();
    }

}
