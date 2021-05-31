package br.com.vicentec12.mygames.data.source.game

import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.data.source.Result

interface GameDataSource {

    suspend fun list(mConsoleId: Long, mOrderBy: Int): Result<List<Game?>>

    suspend fun get(mId: Long): Result<Game>

    suspend fun insert(mGame: Game): Result<Nothing>

    suspend fun update(mGame: Game): Result<Nothing>

    suspend fun delete(mGames: List<Game>): Result<Int>

    suspend fun deleteAll(): Result<Nothing>

}