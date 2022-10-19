package br.com.vicentec12.mygames.domain.repository

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Console

interface ConsoleRepository {

    suspend fun list(): Result<List<Console>>

    suspend fun listWithGames(): Result<List<Console>>

    suspend fun insert(mConsoles: List<Console>): Result<Nothing>

    suspend fun deleteAll(): Result<Nothing>

}