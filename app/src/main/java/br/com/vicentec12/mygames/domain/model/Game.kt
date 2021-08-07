package br.com.vicentec12.mygames.domain.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    var id: Long = 0L,
    var name: String = "",
    var year: String = "",
    var consoleId: Long = 0
) : Parcelable {

    companion object {
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Game, newItem: Game) = oldItem == newItem
        }
    }

}