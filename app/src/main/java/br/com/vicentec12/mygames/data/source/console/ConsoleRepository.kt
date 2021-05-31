package br.com.vicentec12.mygames.data.source.console

import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.source.Local
import javax.inject.Inject

class ConsoleRepository @Inject constructor(
        @Local val consoleLocalDataSource: ConsoleDataSource
) : ConsoleDataSource {

    override suspend fun list() = consoleLocalDataSource.list()

    override suspend fun listWithGames() = consoleLocalDataSource.listWithGames()

    override suspend fun insert(mConsoles: List<Console>) = consoleLocalDataSource.insert(mConsoles)

    override suspend fun delete(mConsole: Console) = consoleLocalDataSource.delete(mConsole)

    override suspend fun deleteAll() = consoleLocalDataSource.deleteAll()

}