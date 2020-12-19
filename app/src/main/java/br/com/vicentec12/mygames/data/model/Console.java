package br.com.vicentec12.mygames.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "console")
public class Console implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    private int image;

    @Ignore
    public Console() {
    }

    @Ignore
    public Console(@NonNull String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Console(int id, @NonNull String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static DiffUtil.ItemCallback<Console> getDiffItemCallback() {
        return new DiffUtil.ItemCallback<Console>() {
            @Override
            public boolean areItemsTheSame(@NonNull Console oldItem, @NonNull Console newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Console oldItem, @NonNull Console newItem) {
                return oldItem.equals(newItem);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Console)) return false;
        Console console = (Console) o;
        return getId() == console.getId() &&
                getImage() == console.getImage() &&
                getName().equals(console.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getImage());
    }

    @Override
    public String toString() {
        return name;
    }

}
