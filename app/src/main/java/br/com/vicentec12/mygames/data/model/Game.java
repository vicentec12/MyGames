package br.com.vicentec12.mygames.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "game")
public class Game implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    private String name;

    private String year;

    public Game(int id, String name, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    @Ignore
    public Game() {
    }

    @Ignore
    public Game(String name, String year) {
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public static DiffUtil.ItemCallback<Game> getDiffItemCallback() {
        return new DiffUtil.ItemCallback<Game>() {
            @Override
            public boolean areItemsTheSame(@NonNull Game oldItem, @NonNull Game newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Game oldItem, @NonNull Game newItem) {
                return oldItem.equals(newItem);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return getId() == game.getId() &&
                Objects.equals(getName(), game.getName()) &&
                Objects.equals(getYear(), game.getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear());
    }

}
