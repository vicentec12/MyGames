package br.com.vicentec12.mygames.data.repository

import br.com.vicentec12.mygames.data.di.Local
import br.com.vicentec12.mygames.data.local.entities.GameEntity
import br.com.vicentec12.mygames.data.source.GameDataSource
import br.com.vicentec12.mygames.domain.repository.GameRepository
import javax.inject.Inject

class GameDataRepository @Inject constructor(
    @Local val gameLocalDataSource: GameDataSource
) : GameRepository {

    override suspend fun list(mConsoleId: Long, mOrderBy: Int) =
        gameLocalDataSource.list(mConsoleId, mOrderBy)

    override suspend fun insert(mGame: GameEntity) = gameLocalDataSource.insert(mGame)

    override suspend fun update(mGame: GameEntity) = gameLocalDataSource.update(mGame)

    override suspend fun delete(mGames: List<GameEntity>) = gameLocalDataSource.delete(mGames)

    override suspend fun deleteAll() = gameLocalDataSource.deleteAll()

}