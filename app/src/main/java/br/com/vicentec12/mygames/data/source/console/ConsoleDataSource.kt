package br.com.vicentec12.mygames.data.source.console

import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.model.ConsoleWithGames
import br.com.vicentec12.mygames.data.source.Result

interface ConsoleDataSource {

    suspend fun list(): Result<List<Console>>

    suspend fun listWithGames(): Result<List<ConsoleWithGames>>

    suspend fun insert(mConsoles: List<Console>): Result<Nothing>

    suspend fun delete(mConsole: Console): Result<Nothing>

    suspend fun deleteAll(): Result<Nothing>

}