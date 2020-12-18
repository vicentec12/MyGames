package br.com.vicentec12.mygames.ui.console;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.databinding.ItemConsoleBinding;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;

public class ConsoleHolder extends RecyclerView.ViewHolder {

    private ItemConsoleBinding mBinding;
    private ConsoleWithGames mConsoleWithGames;
    private OnItemClickListener mOnItemClickListener;

    public ConsoleHolder(@NonNull ItemConsoleBinding mBinding, OnItemClickListener mOnItemClickListener) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void bind(ConsoleWithGames consoleWithGames) {
        this.mConsoleWithGames = consoleWithGames;
        mBinding.setPosition(getAdapterPosition());
        mBinding.setConsoleWithGames(mConsoleWithGames);
        mBinding.setOnItemClickListener(mOnItemClickListener);
    }

}
