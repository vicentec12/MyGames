package br.com.vicentec12.mygames.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "game",
        foreignKeys = @ForeignKey(
                entity = Console.class,
                childColumns = "id_console",
                parentColumns = "_id",
                onDelete = ForeignKey.CASCADE)
)
public class Game implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @NonNull
    private String name;

    private String year;

    @ColumnInfo(name = "id_console")
    private int idConsole;

    public Game(int id, @NonNull String name, String year, int idConsole) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.idConsole = idConsole;
    }

    @Ignore
    public Game() {
        this.name = "";
    }

    @Ignore
    public Game(@NonNull String name, String year) {
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getIdConsole() {
        return idConsole;
    }

    public void setIdConsole(int idConsole) {
        this.idConsole = idConsole;
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
                getIdConsole() == game.getIdConsole() &&
                Objects.equals(getName(), game.getName()) &&
                Objects.equals(getYear(), game.getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear(), getIdConsole());
    }

}
