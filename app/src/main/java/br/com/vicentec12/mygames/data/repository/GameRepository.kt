package br.com.vicentec12.mygames.data.repository

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Game

interface GameRepository {

    suspend fun list(mConsoleId: Long, mOrderBy: Int): Result<List<Game>?>

    suspend fun insert(mGame: Game): Result<Nothing>

    suspend fun update(mGame: Game): Result<Nothing>

    suspend fun delete(mGames: List<Game>): Result<Int>

    suspend fun deleteAll(): Result<Nothing>

}