package br.com.vicentec12.mygames.data.source.console

import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.model.Console
import br.com.vicentec12.mygames.data.source.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    override suspend fun insert(mConsoles: List<Console>) = withContext(Dispatchers.IO) {
        consoleDao.insert(mConsoles)
        Result.Success<Nothing>(message = R.string.message_consoles_inserted)
    }

    override suspend fun delete(mConsole: Console) = withContext(Dispatchers.IO) {
        val deleted = consoleDao.delete(mConsole)
        if (deleted > 0)
            Result.Success(message = R.string.message_console_deleted)
        else
            Result.Error(R.string.error_message_console_delete)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        consoleDao.deleteAll()
        Result.Success<Nothing>(message = R.string.message_console_deleted)
    }

}