package br.com.vicentec12.mygames.util;

import android.content.Context;

import br.com.vicentec12.mygames.data.source.local.AppDatabase;
import br.com.vicentec12.mygames.data.source.local.data_source.GameLocalDataSource;
import br.com.vicentec12.mygames.data.source.repository.GameRepository;

public class InstantiateUtil {

    public static GameRepository instantialeGameRepository(Context context) {
        GameLocalDataSource localDataSource = GameLocalDataSource.getInstance(AppDatabase.getInstance(context),
                AppExecutors.getInstance());
        return GameRepository.getInstance(localDataSource);
    }

}
