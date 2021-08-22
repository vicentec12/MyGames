package br.com.vicentec12.mygames.data.local.source

import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.local.dao.GameDao
import br.com.vicentec12.mygames.data.local.entities.GameEntity
import br.com.vicentec12.mygames.data.local.entities.GameEntity.Companion.COLUMN_NAME
import br.com.vicentec12.mygames.data.source.GameDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameLocalDataSource @Inject constructor(
    private val gameDao: GameDao
) : GameDataSource {

    override suspend fun list(mConsoleId: Long, mOrderBy: Int) = withContext(Dispatchers.IO) {
        val mGames =
            if (mOrderBy == COLUMN_NAME) gameDao.list(mConsoleId) else gameDao.listByYear(mConsoleId)
        if (mGames.isNullOrEmpty())
            Result.Error(0)
        else
            Result.Success(mGames)
    }

    override suspend fun insert(mGame: GameEntity) = withContext(Dispatchers.IO) {
        val mId = gameDao.insert(mGame)
        if (mId > 0)
            Result.Success(message = R.string.message_game_inserted)
        else
            Result.Error(R.string.error_message_game_insert)
    }

    override suspend fun update(mGame: GameEntity) = withContext(Dispatchers.IO) {
        val mId = gameDao.update(mGame)
        if (mId > 0)
            Result.Success(message = R.string.message_game_updated)
        else
            Result.Error(R.string.error_message_game_update)
    }

    override suspend fun delete(mGames: List<GameEntity>) = withContext(Dispatchers.IO) {
        val mNumDeleted = gameDao.delete(mGames)
        if (mNumDeleted > 0) {
            Result.Success(mNumDeleted, R.plurals.plural_message_games_deleted)
        } else
            Result.Error(R.string.error_message_game_delete)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        gameDao.deleteAll()
        Result.Success<Nothing>(message = R.string.message_game_deleted)
    }

}