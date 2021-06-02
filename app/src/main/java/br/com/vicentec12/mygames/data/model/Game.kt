package br.com.vicentec12.mygames.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.vicentec12.mygames.data.model.Game.Companion.COLUMN_CONSOLE_ID
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "game",
        foreignKeys = [ForeignKey(
                entity = Console::class,
                childColumns = [COLUMN_CONSOLE_ID],
                parentColumns = ["id"],
                onDelete = ForeignKey.CASCADE
        )]
)
data class Game(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,
        @NonNull
        var name: String = "",
        @NonNull
        var year: String = "",
        @ColumnInfo(name = COLUMN_CONSOLE_ID)
        var consoleId: Long = 0
) : Parcelable {

    companion object {

        const val COLUMN_NAME = 0
        const val COLUMN_YEAR = 1
        const val COLUMN_CONSOLE_ID = "console_id"

        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Game, newItem: Game) = oldItem == newItem
        }

    }

}