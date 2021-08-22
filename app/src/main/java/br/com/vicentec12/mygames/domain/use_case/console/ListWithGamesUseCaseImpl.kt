package br.com.vicentec12.mygames.domain.use_case.console

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.mapper.toModelList
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import javax.inject.Inject

class ListWithGamesUseCaseImpl @Inject constructor(
    private val mRepository: ConsoleRepository
) : ListWithGamesUseCase {

    override suspend fun invoke(): Result<ArrayList<Console>> =
        when (val result = mRepository.listWithGames()) {
            is Result.Success ->
                Result.Success(result.data?.toModelList() ?: arrayListOf(), result.message)
            is Result.Error -> result
        }

}