package br.com.vicentec12.mygames.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.ui.holder.GameHolder;

public class GameAdapter extends RecyclerView.Adapter<GameHolder> {

    private Context context;
    private List<Game> games;
    private OnItemClickListener onItemClickListener;

    public GameAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameHolder(LayoutInflater.from(context).inflate(R.layout.item_game, parent, false),
                games, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        holder.bindGame(games.get(position));
    }

    @Override
    public int getItemCount() {
        return games == null ? 0 : games.size();
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

}
