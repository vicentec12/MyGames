package br.com.vicentec12.mygames.data.source

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.local.entities.ConsoleEntity
import br.com.vicentec12.mygames.data.local.entities.ConsoleWithGamesEntity

interface ConsoleDataSource {

    suspend fun list(): Result<List<ConsoleEntity>>

    suspend fun listWithGames(): Result<List<ConsoleWithGamesEntity>>

    suspend fun insert(mConsoles: List<ConsoleEntity>): Result<Nothing>

    suspend fun deleteAll(): Result<Nothing>

}