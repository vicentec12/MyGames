package br.com.vicentec12.mygames.ui.game;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.ListAdapter;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.databinding.ItemGameBinding;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.interfaces.OnItemLongClickListener;

public class GameAdapter extends ListAdapter<Game, GameHolder> {

    private final GameViewModel mViewModel;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public GameAdapter(GameViewModel mViewModel) {
        super(Game.getDiffItemCallback());
        this.mViewModel = mViewModel;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGameBinding mBinding = ItemGameBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new GameHolder(mBinding, mOnItemClickListener, mOnItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        holder.bindGame(getItem(position), mViewModel);
    }

    // Responsável por resolver glichs quando notifyDataSetChange era chamado
    @Override
    public long getItemId(int position) {
        Game game = getItem(position);
        return game.getId();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

}
