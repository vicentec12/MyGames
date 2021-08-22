package br.com.vicentec12.mygames.domain.repository

import br.com.vicentec12.mygames.data.local.entities.GameEntity
import br.com.vicentec12.mygames.data.Result

interface GameRepository {

    suspend fun list(mConsoleId: Long, mOrderBy: Int): Result<List<GameEntity>?>

    suspend fun insert(mGame: GameEntity): Result<Nothing>

    suspend fun update(mGame: GameEntity): Result<Nothing>

    suspend fun delete(mGames: List<GameEntity>): Result<Int>

    suspend fun deleteAll(): Result<Nothing>

}