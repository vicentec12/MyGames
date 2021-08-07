package br.com.vicentec12.mygames.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Console(
    val id: Long = 0L,
    val name: String = "",
    @DrawableRes val image: Int = 0,
    val games: ArrayList<Game>? = null
) : Parcelable {

    override fun toString() = name

    companion object {

        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Console>() {
            override fun areItemsTheSame(oldItem: Console, newItem: Console) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Console, newItem: Console) = oldItem == newItem
        }

    }

}