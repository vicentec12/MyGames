package br.com.vicentec12.mygames.extensions;

import android.widget.ViewFlipper;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.ui.game.GameAdapter;

public class BindingAdapters {

    @BindingAdapter(value = "displayedChild")
    public static void setDisplayedChild(ViewFlipper view, int mValue) {
        if (view.getDisplayedChild() != mValue)
            view.setDisplayedChild(mValue);
    }

    @BindingAdapter(value = "items")
    public static void setItemsGame(RecyclerView recyclerView, List<Game> games) {
        GameAdapter mAdapter = (GameAdapter) recyclerView.getAdapter();
        if (mAdapter != null)
            mAdapter.submitList(games);
    }

    @BindingAdapter(value = "srcVector")
    public static void setFabIcon(FloatingActionButton view, int resourceId) {
        view.setImageResource(resourceId);
    }

}