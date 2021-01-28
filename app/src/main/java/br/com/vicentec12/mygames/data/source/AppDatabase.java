package br.com.vicentec12.mygames.data.source;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.console.ConsoleDao;
import br.com.vicentec12.mygames.data.source.game.GameDao;

@Database(entities = {Console.class, Game.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "mygames.db";

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Cria tabela console
            database.execSQL("CREATE TABLE IF NOT EXISTS console (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "image INTEGER NOT NULL)");
            // Cria nova tabela game
            database.execSQL("CREATE TABLE IF NOT EXISTS game_new (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "year TEXT," +
                    "id_console INTEGER NOT NULL," +
                    "FOREIGN KEY (id_console) REFERENCES console (id) ON UPDATE NO ACTION ON DELETE CASCADE)");
            // Insere os valores da tabela antiga
            database.execSQL("INSERT INTO game_new (id, name, year, id_console) " +
                    "SELECT id, name, year, 0 FROM game");
            // Exclui tabela antiga
            database.execSQL("DROP TABLE game");
            // Renomeia banco
            database.execSQL("ALTER TABLE game_new RENAME TO game");
        }
    };

    public abstract ConsoleDao getConsoleDao();

    public abstract GameDao getGameDao();

}
