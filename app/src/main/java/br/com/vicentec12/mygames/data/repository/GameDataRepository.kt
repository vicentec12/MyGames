package br.com.vicentec12.mygames.data.repository

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.di.Local
import br.com.vicentec12.mygames.data.mapper.toEntity
import br.com.vicentec12.mygames.data.mapper.toEntityList
import br.com.vicentec12.mygames.data.mapper.toModelList
import br.com.vicentec12.mygames.data.source.GameDataSource
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.repository.GameRepository
import javax.inject.Inject

class GameDataRepository @Inject constructor(
    @Local val gameLocalDataSource: GameDataSource
) : GameRepository {

    override suspend fun list(mConsoleId: Long, mOrderBy: Int) =
        when (val result = gameLocalDataSource.list(mConsoleId, mOrderBy)) {
            is Result.Success -> Result.Success(result.data?.toModelList(), result.message)
            is Result.Error -> result
        }

    override suspend fun insert(mGame: Game) = gameLocalDataSource.insert(mGame.toEntity())

    override suspend fun update(mGame: Game) = gameLocalDataSource.update(mGame.toEntity())

    override suspend fun delete(mGames: List<Game>) =
        gameLocalDataSource.delete(mGames.toEntityList())

    override suspend fun deleteAll() = gameLocalDataSource.deleteAll()

}