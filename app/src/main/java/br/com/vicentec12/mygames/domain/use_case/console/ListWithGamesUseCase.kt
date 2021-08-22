package br.com.vicentec12.mygames.domain.use_case.console

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Console

interface ListWithGamesUseCase {
    suspend operator fun invoke(): Result<ArrayList<Console>>
}