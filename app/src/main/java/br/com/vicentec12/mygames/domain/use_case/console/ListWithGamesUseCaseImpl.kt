package br.com.vicentec12.mygames.domain.use_case.console

import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import javax.inject.Inject

class ListWithGamesUseCaseImpl @Inject constructor(
    private val mRepository: ConsoleRepository
) : ListWithGamesUseCase {

    override suspend fun invoke() = mRepository.listWithGames()
}