package br.com.vicentec12.mygames.ui.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.interfaces.OnItemLongClickListener;

public class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private AppCompatTextView _tvName;
    private AppCompatTextView _tvYear;
    private AppCompatCheckBox _cbChecked;

    private Game game;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public GameHolder(@NonNull View itemView, OnItemClickListener onItemClickListener,
                      OnItemLongClickListener onItemLongClickListener) {
        super(itemView);
        _tvName = itemView.findViewById(R.id.tv_item_game_name);
        _tvYear = itemView.findViewById(R.id.tv_item_game_year);
        _cbChecked = itemView.findViewById(R.id.cb_item_game_checked);
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bindGame(Game game, boolean isSelectionMode, boolean isSelected) {
        this.game = game;
        _tvName.setText(game.getName());
        _tvYear.setText(game.getYear());
        _cbChecked.setVisibility(isSelectionMode ? View.VISIBLE : View.GONE);
        _cbChecked.setChecked(isSelected);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            _cbChecked.setChecked(!_cbChecked.isChecked());
            onItemClickListener.onItemClickListener(v, getAdapterPosition(), this.game);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemLongClickListener != null) {
            _cbChecked.setChecked(!_cbChecked.isChecked());
            onItemLongClickListener.onItemLongClickListener(v, getAdapterPosition(), this.game);
            return true;
        }
        return false;
    }
}
