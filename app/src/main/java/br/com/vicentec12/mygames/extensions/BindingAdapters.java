package br.com.vicentec12.mygames.extensions;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.ui.console.ConsoleAdapter;
import br.com.vicentec12.mygames.ui.game.GameAdapter;

public class BindingAdapters {

    @BindingAdapter("displayedChild")
    public static void setDisplayedChild(ViewFlipper view, int mValue) {
        if (view.getDisplayedChild() != mValue)
            view.setDisplayedChild(mValue);
    }

    @BindingAdapter("items")
    public static void setItemsGame(RecyclerView recyclerView, List<Game> mGames) {
        GameAdapter mAdapter = (GameAdapter) recyclerView.getAdapter();
        if (mAdapter != null)
            mAdapter.submitList(mGames);
    }

    @BindingAdapter("items")
    public static void setItemsConsole(RecyclerView recyclerView, List<ConsoleWithGames> mConsoles) {
        ConsoleAdapter mAdapter = (ConsoleAdapter) recyclerView.getAdapter();
        if (mAdapter != null)
            mAdapter.submitList(mConsoles);
    }

    @BindingAdapter("items")
    public static void setItemsConsoleSpinner(AppCompatSpinner spinner, List<Console> mConsoles) {
        if (mConsoles == null)
            mConsoles = new ArrayList<>();
        mConsoles.add(0, new Console(0, spinner.getContext().getString(R.string.text_select), 0));
        ArrayAdapter<Console> mAdapter = new ArrayAdapter<>(spinner.getContext(),
                R.layout.support_simple_spinner_dropdown_item, mConsoles);
        mAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        if (mAdapter != null)
            spinner.setAdapter(mAdapter);
    }

    @BindingAdapter(value = "srcVector")
    public static void setFabIcon(FloatingActionButton view, int mResourceId) {
        view.setImageResource(mResourceId);
    }

    @BindingAdapter(value = "srcVector")
    public static void setImageViewIcon(AppCompatImageView view, int mResourceId) {
        view.setImageResource(mResourceId);
    }

}
