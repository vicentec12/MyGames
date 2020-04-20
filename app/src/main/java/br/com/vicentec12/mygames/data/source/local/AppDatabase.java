package br.com.vicentec12.mygames.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.local.dao.GameDao;
import br.com.vicentec12.mygames.util.AppExecutors;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "mygames.db";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract GameDao getGameDao();

    /**
     * Método responsável por limpar todas as tabelas do banco de dados interno.
     */
    public static void clearDatabase(Context context) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            getInstance(context).getGameDao().deleteAll();
        });
    }
}
