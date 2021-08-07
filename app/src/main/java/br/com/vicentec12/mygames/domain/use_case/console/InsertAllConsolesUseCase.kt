package br.com.vicentec12.mygames.domain.use_case.console

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Console

interface InsertAllConsolesUseCase {
    suspend operator fun invoke(mConsoles: List<Console>): Result<Nothing>
}