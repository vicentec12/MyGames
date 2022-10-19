package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.mapper.toEntity
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.repository.GameRepository
import javax.inject.Inject

class UpdateGameUseCaseImpl @Inject constructor(
    private val mRepository: GameRepository
) : UpdateGameUseCase {

    override suspend fun invoke(mGame: Game): Result<Nothing> = mRepository.update(mGame)

}