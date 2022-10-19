package br.com.vicentec12.mygames.data.repository

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.di.Local
import br.com.vicentec12.mygames.data.mapper.toEntityList
import br.com.vicentec12.mygames.data.mapper.toModelList
import br.com.vicentec12.mygames.data.source.ConsoleDataSource
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import javax.inject.Inject

class ConsoleDataRepository @Inject constructor(
    @Local val consoleLocalDataSource: ConsoleDataSource
) : ConsoleRepository {

    override suspend fun list() = when (val result = consoleLocalDataSource.list()) {
        is Result.Success -> Result.Success(result.data?.toModelList().orEmpty(), result.message)
        is Result.Error -> result
    }

    override suspend fun listWithGames() = when (val result = consoleLocalDataSource.listWithGames()) {
            is Result.Success -> Result.Success(result.data?.toModelList().orEmpty(), result.message)
            is Result.Error -> result
        }

    override suspend fun insert(mConsoles: List<Console>) =
        consoleLocalDataSource.insert(mConsoles.toEntityList())

    override suspend fun deleteAll() = consoleLocalDataSource.deleteAll()

}