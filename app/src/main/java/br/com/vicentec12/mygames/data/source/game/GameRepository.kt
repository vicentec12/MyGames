package br.com.vicentec12.mygames.data.source.game

import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.data.source.di.Local
import javax.inject.Inject

class GameRepository @Inject constructor(
        @Local val gameLocalDataSource: GameDataSource
) : GameDataSource {

    override suspend fun list(mConsoleId: Long, mOrderBy: Int) = gameLocalDataSource.list(mConsoleId, mOrderBy)

    override suspend fun get(mId: Long) = gameLocalDataSource.get(mId)

    override suspend fun insert(mGame: Game) = gameLocalDataSource.insert(mGame)

    override suspend fun update(mGame: Game) = gameLocalDataSource.update(mGame)

    override suspend fun delete(mGames: List<Game>) = gameLocalDataSource.delete(mGames)

    override suspend fun deleteAll() = gameLocalDataSource.deleteAll()

}