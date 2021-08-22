package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.mapper.toModelList
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.domain.repository.GameRepository
import javax.inject.Inject

class ListGamesUseCaseImpl @Inject constructor(
    private val mRepository: GameRepository
) : ListGamesUseCase {

    override suspend fun invoke(mConsoleId: Long, mOrderBy: Int): Result<ArrayList<Game>> =
        when (val result = mRepository.list(mConsoleId, mOrderBy)) {
            is Result.Success ->
                Result.Success(result.data?.toModelList() ?: arrayListOf(), result.message)
            is Result.Error -> result
        }

}