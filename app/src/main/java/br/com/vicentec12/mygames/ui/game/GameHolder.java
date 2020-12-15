package br.com.vicentec12.mygames.ui.game;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.databinding.ItemGameBinding;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.interfaces.OnItemLongClickListener;

public class GameHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    private Game game;
    private ItemGameBinding mBinding;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public GameHolder(@NonNull ItemGameBinding mBinding, OnItemClickListener onItemClickListener,
                      OnItemLongClickListener onItemLongClickListener) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
        itemView.setOnLongClickListener(this);
    }

    public void bindGame(Game game, GameViewModel mViewModel) {
        this.game = game;
        mBinding.setGame(game);
        mBinding.setViewModel(mViewModel);
        mBinding.setPosition(getAdapterPosition());
        mBinding.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(v, this.game, getAdapterPosition());
            return true;
        }
        return false;
    }

}
