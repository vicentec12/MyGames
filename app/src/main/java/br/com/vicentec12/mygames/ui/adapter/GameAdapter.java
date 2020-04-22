package br.com.vicentec12.mygames.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.interfaces.OnItemLongClickListener;
import br.com.vicentec12.mygames.ui.holder.GameHolder;

public class GameAdapter extends RecyclerView.Adapter<GameHolder> {

    private Context context;
    private List<Game> games;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private SparseBooleanArray mSelectedItems;
    private boolean mSelectionMode;

    public GameAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
        this.mSelectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameHolder(LayoutInflater.from(context).inflate(R.layout.item_game, parent, false),
                onItemClickListener, onItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        holder.bindGame(games.get(position), isSelectionMode(), mSelectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return games == null ? 0 : games.size();
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    public void insertItem(Game game) {
        games.add(game);
        notifyItemInserted(getItemCount());
    }

    public void removeItem(int position) {
        games.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void select(int position) {
        if (mSelectedItems.get(position, false))
            mSelectedItems.delete(position);
        else
            mSelectedItems.put(position, true);
    }

    public void selectAll(boolean isTodosSelecionados) {
        if (!isTodosSelecionados) mSelectedItems.clear();
        for (int i = 0; i < games.size(); i++) {
            select(i);
        }
        notifyDataSetChanged();
    }

    public void showChecks(boolean isSelectionMode) {
        this.mSelectionMode = isSelectionMode;
        notifyDataSetChanged();
    }

    public void clearSelection() {
        mSelectedItems.clear();
        showChecks(false);
    }

    public List<Game> getGames() {
        return games;
    }

    public List<Game> getSelectedGames() {
        List<Game> selectedGames = new ArrayList<>();
        for (int i = 0; i < mSelectedItems.size(); i++) {
            selectedGames.add(games.get(mSelectedItems.keyAt(i)));
        }
        return selectedGames;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }

    public void setSelectedItems(List<Integer> selectedItems) {
        for (int i = 0; i < selectedItems.size(); i++) {
            mSelectedItems.put(selectedItems.get(i), true);
        }
    }

    public boolean isSelectionMode() {
        return mSelectionMode;
    }

    public void setSelectionMode(boolean mSelectionMode) {
        this.mSelectionMode = mSelectionMode;
    }

}
