package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.domain.repository.GameRepository
import javax.inject.Inject

class ListGamesUseCaseImpl @Inject constructor(
    private val mRepository: GameRepository
) : ListGamesUseCase {

    override suspend fun invoke(mConsoleId: Long, mOrderBy: Int) =
        mRepository.list(mConsoleId, mOrderBy)
}