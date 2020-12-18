package br.com.vicentec12.mygames.ui.console;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.databinding.ItemConsoleBinding;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;

public class ConsoleAdapter extends ListAdapter<ConsoleWithGames, ConsoleHolder> {

    private OnItemClickListener mOnItemClickListener;

    protected ConsoleAdapter() {
        super(ConsoleWithGames.getDiffItemCallback());
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ConsoleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConsoleBinding mBinding = ItemConsoleBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ConsoleHolder(mBinding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsoleHolder holder, int position) {
        holder.bind(getItem(position));
    }

}
