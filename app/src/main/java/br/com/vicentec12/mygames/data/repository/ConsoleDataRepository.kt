package br.com.vicentec12.mygames.data.repository

import br.com.vicentec12.mygames.data.mapper.toEntityList
import br.com.vicentec12.mygames.data.mapper.toModelList
import br.com.vicentec12.mygames.data.source.ConsoleDataSource
import br.com.vicentec12.mygames.di.Local
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import br.com.vicentec12.mygames.extensions.map
import javax.inject.Inject

class ConsoleDataRepository @Inject constructor(
    @Local val consoleLocalDataSource: ConsoleDataSource
) : ConsoleRepository {

    override suspend fun list() =
        consoleLocalDataSource.list().map { data -> data?.toModelList().orEmpty() }

    override suspend fun listWithGames() =
        consoleLocalDataSource.listWithGames().map { data -> data?.toModelList().orEmpty() }

    override suspend fun insert(mConsoles: List<Console>) =
        consoleLocalDataSource.insert(mConsoles.toEntityList())

    override suspend fun deleteAll() = consoleLocalDataSource.deleteAll()

}