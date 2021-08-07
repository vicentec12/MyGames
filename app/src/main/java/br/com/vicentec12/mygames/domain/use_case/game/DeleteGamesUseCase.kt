package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Game

interface DeleteGamesUseCase {
    suspend operator fun invoke(mGames: List<Game>): Result<Int>
}