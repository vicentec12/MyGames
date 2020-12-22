package br.com.vicentec12.mygames.data.source;

import androidx.room.Room;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import br.com.vicentec12.mygames.data.source.AppDatabase;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {

    private static final String TEST_DB = "migration_test";

    @Rule
    public MigrationTestHelper helper;

    public MigrationTest() {
        this.helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                AppDatabase.class.getCanonicalName(), new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrate1To2() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);
        db.close();
        helper.runMigrationsAndValidate(TEST_DB, 2, true, AppDatabase.MIGRATION_1_2);
    }

}
