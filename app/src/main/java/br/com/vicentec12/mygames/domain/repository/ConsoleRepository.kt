package br.com.vicentec12.mygames.domain.repository

import br.com.vicentec12.mygames.data.local.entities.ConsoleEntity
import br.com.vicentec12.mygames.data.local.entities.ConsoleWithGamesEntity
import br.com.vicentec12.mygames.data.Result

interface ConsoleRepository {

    suspend fun list(): Result<List<ConsoleEntity>>

    suspend fun listWithGames(): Result<List<ConsoleWithGamesEntity>>

    suspend fun insert(mConsoles: List<ConsoleEntity>): Result<Nothing>

    suspend fun deleteAll(): Result<Nothing>

}