package br.com.vicentec12.mygames.domain.use_case.console

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import javax.inject.Inject

class DeleteAllConsolesUseCaseImpl @Inject constructor(
    private val mRepository: ConsoleRepository
) : DeleteAllConsolesUseCase {

    override suspend fun invoke(): Result<Nothing> = mRepository.deleteAll()

}