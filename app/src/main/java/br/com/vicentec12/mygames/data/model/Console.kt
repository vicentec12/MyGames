package br.com.vicentec12.mygames.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "console")
data class Console(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0L,
        @NonNull
        val name: String = "",
        @DrawableRes
        val image: Int = 0
) : Parcelable {

    override fun toString() = name

    companion object {

        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Console>() {
            override fun areItemsTheSame(oldItem: Console, newItem: Console) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Console, newItem: Console) = oldItem == newItem
        }

    }

}