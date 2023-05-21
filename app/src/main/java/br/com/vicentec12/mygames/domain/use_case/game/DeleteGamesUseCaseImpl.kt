package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.data.repository.GameRepository
import javax.inject.Inject

class DeleteGamesUseCaseImpl @Inject constructor(
    private val mRepository: GameRepository
) : DeleteGamesUseCase {

    override suspend fun invoke(mGames: List<Game>): Result<Int> =
        mRepository.delete(mGames)

}