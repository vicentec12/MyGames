package br.com.vicentec12.mygames.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import br.com.vicentec12.mygames.data.local.entities.GameEntity.Companion.COLUMN_CONSOLE_ID

data class ConsoleWithGamesEntity(
    @Embedded
    val console: ConsoleEntity,
    @Relation(parentColumn = "id", entityColumn = COLUMN_CONSOLE_ID)
    val games: List<GameEntity>
)