package br.com.vicentec12.mygames.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Embedded
import androidx.room.Relation
import br.com.vicentec12.mygames.data.model.Game.Companion.COLUMN_CONSOLE_ID
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConsoleWithGames(
        @Embedded
        val console: Console,
        @Relation(parentColumn = "id", entityColumn = COLUMN_CONSOLE_ID)
        val games: List<Game>
) : Parcelable {

    companion object {

        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<ConsoleWithGames>() {
            override fun areItemsTheSame(oldItem: ConsoleWithGames, newItem: ConsoleWithGames) =
                    oldItem.console.id == newItem.console.id

            override fun areContentsTheSame(oldItem: ConsoleWithGames, newItem: ConsoleWithGames) = oldItem == newItem
        }

    }

}