package br.com.vicentec12.mygames.data.source.console;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.interfaces.Callbacks;

public interface ConsoleDataSource {

    interface OnConsolesListedCallback {

        void onSucess(int message, List<ConsoleWithGames> consolesWithGames);

        void onErro(int message);

    }

    void listConsolesWithGames(OnConsolesListedCallback callback);

    void insertConsoles(List<Console> consoles, Callbacks.OnLocalCallback callback);

    void deleteConsole(Console console, Callbacks.OnLocalCallback callback);

    void deleteAllConsoles(Callbacks.OnLocalCallback callback);

}
