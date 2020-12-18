package br.com.vicentec12.mygames.util;

import android.content.Context;

import br.com.vicentec12.mygames.data.source.AppDatabase;
import br.com.vicentec12.mygames.data.source.console.ConsoleLocalDataSource;
import br.com.vicentec12.mygames.data.source.console.ConsoleRepository;
import br.com.vicentec12.mygames.data.source.game.GameLocalDataSource;
import br.com.vicentec12.mygames.data.source.game.GameRepository;
import br.com.vicentec12.mygames.extensions.AppExecutors;

public class InstantiateUtil {

    public static GameRepository initGameRepository(Context context) {
        GameLocalDataSource localDataSource = GameLocalDataSource.getInstance(AppDatabase.getInstance(context),
                AppExecutors.getInstance());
        return GameRepository.getInstance(localDataSource);
    }

    public static ConsoleRepository initConsoleRepository(Context context) {
        ConsoleLocalDataSource localDataSource = ConsoleLocalDataSource.getInstance(AppDatabase.getInstance(context),
                AppExecutors.getInstance());
        return ConsoleRepository.getInstance(localDataSource);
    }

}
