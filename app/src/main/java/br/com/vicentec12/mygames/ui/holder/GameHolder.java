package br.com.vicentec12.mygames.ui.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;

public class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private AppCompatTextView _tvName;
    private AppCompatTextView _tvYear;

    private List<Game> games;
    private OnItemClickListener onItemClickListener;

    public GameHolder(@NonNull View itemView, List<Game> games, OnItemClickListener onItemClickListener) {
        super(itemView);
        _tvName = itemView.findViewById(R.id.tv_item_game_name);
        _tvYear = itemView.findViewById(R.id.tv_item_game_year);
        this.games = games;
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    public void bindGame(Game game) {
        _tvName.setText(game.getName());
        _tvYear.setText(game.getYear());
    }

    @Override
    public void onClick(View v) {

    }

}
