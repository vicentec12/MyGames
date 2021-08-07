package br.com.vicentec12.mygames.domain.use_case.console

import br.com.vicentec12.mygames.data.Result

interface DeleteAllConsolesUseCase {
    suspend operator fun invoke(): Result<Nothing>
}