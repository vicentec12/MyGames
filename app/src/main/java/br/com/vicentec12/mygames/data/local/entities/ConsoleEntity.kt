package br.com.vicentec12.mygames.data.local.entities

import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "console")
data class ConsoleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @NonNull val name: String = "",
    @DrawableRes val image: Int = 0
)