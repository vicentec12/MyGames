package br.com.vicentec12.mygames.data.local.source

import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.local.dao.ConsoleDao
import br.com.vicentec12.mygames.data.local.entities.ConsoleEntity
import br.com.vicentec12.mygames.data.source.ConsoleDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConsoleLocalDataSource @Inject constructor(
    private val consoleDao: ConsoleDao
) : ConsoleDataSource {

    override suspend fun list() = withContext(Dispatchers.IO) {
        val consoles = consoleDao.list()
        if (consoles.isNullOrEmpty())
            Result.Error(R.string.error_message_consoles_listed)
        else
            Result.Success(consoles, R.string.message_consoles_listed)
    }

    override suspend fun listWithGames() = withContext(Dispatchers.IO) {
        val consoles = consoleDao.listWithGames()
        if (consoles.isNullOrEmpty())
            Result.Error(R.string.error_message_consoles_listed)
        else
            Result.Success(consoles, R.string.message_consoles_listed)
    }

    override suspend fun insert(mConsoles: List<ConsoleEntity>) = withContext(Dispatchers.IO) {
        consoleDao.insert(mConsoles)
        Result.Success<Nothing>(message = R.string.message_consoles_inserted)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        consoleDao.deleteAll()
        Result.Success<Nothing>(message = R.string.message_console_deleted)
    }

}