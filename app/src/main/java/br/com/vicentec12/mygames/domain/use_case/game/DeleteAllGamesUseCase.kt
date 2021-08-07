package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result

interface DeleteAllGamesUseCase {
    suspend operator fun invoke(): Result<Nothing>
}