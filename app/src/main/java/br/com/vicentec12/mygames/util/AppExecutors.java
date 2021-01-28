package br.com.vicentec12.mygames.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Classe responsável por executar operações fora da Thread principal.
 * Previnindo que operações de banco de dados interno e externo parem o aplicativo.
 */
@Singleton
public class AppExecutors {

    /**Chamar quando executar operação que envolva armazenamento do aparelho.
     * Ex.: Requisições ao banco de dados interno. */
    private final Executor diskIO;
    /** Chamar quando executar operações que envolvam a Thread Principal.
     *  Ex.: Modificações na parte visual do aplicativo. */
    private final Executor mainThread;
    /** Chamar quando executar operação que envolva conexão com internet.
     *  Ex.: Camadas do método de requisições do Volley. */
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    @Inject
    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }

    }

}
