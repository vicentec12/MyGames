package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.repository.GameRepository
import javax.inject.Inject

class DeleteAllGamesUseCaseImpl @Inject constructor(
    private val mRepository: GameRepository
) : DeleteAllGamesUseCase {

    override suspend fun invoke(): Result<Nothing> = mRepository.deleteAll()

}