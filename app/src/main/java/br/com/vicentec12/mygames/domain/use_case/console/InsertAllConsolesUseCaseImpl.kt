package br.com.vicentec12.mygames.domain.use_case.console

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.mapper.toEntityList
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.repository.ConsoleRepository
import javax.inject.Inject

class InsertAllConsolesUseCaseImpl @Inject constructor(
    private val mRepository: ConsoleRepository
) : InsertAllConsolesUseCase {

    override suspend fun invoke(mConsoles: List<Console>): Result<Nothing> =
        mRepository.insert(mConsoles)
    
}