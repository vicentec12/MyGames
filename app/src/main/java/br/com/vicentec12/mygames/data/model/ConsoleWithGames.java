package br.com.vicentec12.mygames.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

public class ConsoleWithGames {

    @Embedded
    private Console console;

    @Relation(parentColumn = "id", entityColumn = "id_console")
    private List<Game> games;

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public static DiffUtil.ItemCallback<ConsoleWithGames> getDiffItemCallback() {
        return new DiffUtil.ItemCallback<ConsoleWithGames>() {
            @Override
            public boolean areItemsTheSame(@NonNull ConsoleWithGames oldItem, @NonNull ConsoleWithGames newItem) {
                return oldItem.getConsole().getId() == newItem.getConsole().getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull ConsoleWithGames oldItem, @NonNull ConsoleWithGames newItem) {
                return oldItem.equals(newItem);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsoleWithGames)) return false;
        ConsoleWithGames that = (ConsoleWithGames) o;
        return Objects.equals(getConsole(), that.getConsole()) &&
                Objects.equals(getGames(), that.getGames());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConsole(), getGames());
    }

}
