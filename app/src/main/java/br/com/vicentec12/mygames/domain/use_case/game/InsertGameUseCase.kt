package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Game

interface InsertGameUseCase {
    suspend operator fun invoke(mGame: Game): Result<Nothing>
}