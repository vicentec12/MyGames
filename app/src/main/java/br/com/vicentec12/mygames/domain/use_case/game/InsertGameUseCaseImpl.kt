package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.data.repository.GameRepository
import javax.inject.Inject

class InsertGameUseCaseImpl @Inject constructor(
    private val mRepository: GameRepository
) : InsertGameUseCase {

    override suspend fun invoke(mGame: Game): Result<Nothing> = mRepository.insert(mGame)

}