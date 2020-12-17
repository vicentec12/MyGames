package br.com.vicentec12.mygames.data.source;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.game.GameDao;
import br.com.vicentec12.mygames.extensions.AppExecutors;

@Database(entities = {Console.class, Game.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "mygames.db";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2).fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Cria tabela console
            database.execSQL("CREATE TABLE IF NOT EXISTS console (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name TEXT NOT NULL)");
            // Cria nova tabela game
            database.execSQL("CREATE TABLE IF NOT EXISTS game_new (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "year TEXT," +
                    "id_console INTEGER NOT NULL," +
                    "FOREIGN KEY (id_console) REFERENCES console (_id) ON UPDATE NO ACTION ON DELETE CASCADE)");
            // Insere os valores da tabela antiga
            database.execSQL("INSERT INTO game_new (_id, name, year, id_console) " +
                    "SELECT _id, name, year, 0 FROM game");
            // Exclui tabela antiga
            database.execSQL("DROP TABLE game");
            // Renomeia banco
            database.execSQL("ALTER TABLE game_new RENAME TO game");
        }
    };

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


