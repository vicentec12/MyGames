package br.com.vicentec12.mygames.domain.use_case.game

import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.domain.model.Game

interface ListGamesUseCase {
    suspend operator fun invoke(mConsoleId: Long, mOrderBy: Int): Result<ArrayList<Game>>
}