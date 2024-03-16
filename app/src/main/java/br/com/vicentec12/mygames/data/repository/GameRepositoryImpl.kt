package br.com.vicentec12.mygames.data.repository

import br.com.vicentec12.mygames.data.mapper.toEntity
import br.com.vicentec12.mygames.data.mapper.toEntityList
import br.com.vicentec12.mygames.data.mapper.toModelList
import br.com.vicentec12.mygames.data.source.GameDataSource
import br.com.vicentec12.mygames.commons.di.Local
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.commons.extensions.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    @Local val gameLocalDataSource: GameDataSource
) : GameRepository {

    override suspend fun list(mConsoleId: Long, mOrderBy: Int) = withContext(Dispatchers.IO) {
        gameLocalDataSource.list(mConsoleId, mOrderBy).map { data -> data?.toModelList() }
    }

    override suspend fun insert(mGame: Game) = withContext(Dispatchers.IO) {
        gameLocalDataSource.insert(mGame.toEntity())
    }

    override suspend fun update(mGame: Game) = withContext(Dispatchers.IO) {
        gameLocalDataSource.update(mGame.toEntity())
    }

    override suspend fun delete(mGames: List<Game>) = withContext(Dispatchers.IO) {
        gameLocalDataSource.delete(mGames.toEntityList())
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        gameLocalDataSource.deleteAll()
    }

}