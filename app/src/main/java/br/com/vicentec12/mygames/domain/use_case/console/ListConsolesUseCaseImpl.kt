package br.com.vicentec12.mygames.domain.use_case.console

import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import javax.inject.Inject

class ListConsolesUseCaseImpl @Inject constructor(
    private val mRepository: ConsoleRepository
) : ListConsolesUseCase {

    override suspend fun invoke() = mRepository.list()
}