package br.com.vicentec12.mygames.data.repository

import br.com.vicentec12.mygames.data.di.Local
import br.com.vicentec12.mygames.data.local.entities.ConsoleEntity
import br.com.vicentec12.mygames.data.source.ConsoleDataSource
import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import javax.inject.Inject

class ConsoleDataRepository @Inject constructor(
    @Local val consoleLocalDataSource: ConsoleDataSource
) : ConsoleRepository {

    override suspend fun list() = consoleLocalDataSource.list()

    override suspend fun listWithGames() = consoleLocalDataSource.listWithGames()

    override suspend fun insert(mConsoles: List<ConsoleEntity>) =
        consoleLocalDataSource.insert(mConsoles)

    override suspend fun deleteAll() = consoleLocalDataSource.deleteAll()

}