package br.com.vicentec12.mygames.data.repository

import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.data.Result
import br.com.vicentec12.mygames.data.mapper.toEntityList
import br.com.vicentec12.mygames.data.mapper.toModelList
import br.com.vicentec12.mygames.data.source.ConsoleDataSource
import br.com.vicentec12.mygames.di.Local
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.extensions.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConsoleRepositoryImpl @Inject constructor(
    @Local val consoleLocalDataSource: ConsoleDataSource
) : ConsoleRepository {

    override suspend fun list() = withContext(Dispatchers.IO) {
        consoleLocalDataSource.list().map { data -> data?.toModelList().orEmpty() }
    }

    override suspend fun listWithGames() = withContext(Dispatchers.IO) {
        when (val result = consoleLocalDataSource.listWithGames()) {
            is Result.Success -> result.map { data -> data?.toModelList().orEmpty() }
            is Result.Error -> {
                deleteAll()
                insertConsoles()
            }
        }
    }

    override suspend fun insert(mConsoles: List<Console>) = withContext(Dispatchers.IO) {
        consoleLocalDataSource.insert(mConsoles.toEntityList())
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        consoleLocalDataSource.deleteAll()
    }

    private suspend fun insertConsoles(): Result<List<Console>> {
        val mConsoles = listOf(
            Console(name = "Nintendo Entertainment System", image = R.drawable.lg_nes),
            Console(name = "Super Nintendo", image = R.drawable.lg_snes),
            Console(name = "Nintendo 64", image = R.drawable.lg_n64),
            Console(name = "Nintendo Gamecube", image = R.drawable.lg_gc),
            Console(name = "Nintendo Wii", image = R.drawable.lg_wii),
            Console(name = "Nintendo 3DS", image = R.drawable.lg_3ds),
            Console(name = "Nintendo DS", image = R.drawable.lg_nds),
            Console(name = "Nintendo Switch", image = R.drawable.lg_ns),
            Console(name = "PlayStation", image = R.drawable.lg_ps1),
            Console(name = "PlayStation 2", image = R.drawable.lg_ps2),
            Console(name = "PlayStation 3", image = R.drawable.lg_ps3),
            Console(name = "PlayStation 4", image = R.drawable.lg_ps4),
            Console(name = "Xbox", image = R.drawable.lg_xbox),
            Console(name = "Xbox 360", image = R.drawable.lg_x360),
            Console(name = "Xbox One", image = R.drawable.lg_xone)
        )
        insert(mConsoles)
        return listWithGames()
    }

}