package br.com.vicentec12.mygames.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.Callbacks;
import br.com.vicentec12.mygames.data.source.GameDataSource;
import br.com.vicentec12.mygames.data.source.repository.GameRepository;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.interfaces.OnItemLongClickListener;
import br.com.vicentec12.mygames.ui.adapter.GameAdapter;
import br.com.vicentec12.mygames.util.InstantiateUtil;

public class MainActivity extends AppCompatActivity implements ActionMode.Callback, OnItemClickListener, OnItemLongClickListener {

    public static final int CODE_OPERATION_SUCCESS = 2907;

    private RecyclerView _rvGames;
    private AppCompatTextView _tvErrorMessage;
    private FloatingActionButton _fabAdd;

    private ActionMode mActionMode;
    private GameAdapter mGameAdapter;
    private GameRepository mGameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onRestoreSavedInstanceState(savedInstanceState);
        init();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            if (mGameAdapter != null) {
                outState.putSerializable("games", (Serializable) mGameAdapter.getGames());
                outState.putIntegerArrayList("selectedItems", (ArrayList<Integer>) mGameAdapter.getSelectedItems());
                outState.putBoolean("isSelectionMode", mGameAdapter.isSelectionMode());
            }
        }
    }

    private void onRestoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<Game> games = (List<Game>) savedInstanceState.getSerializable("games");
            if (games != null) {
                boolean isSelectionMode = savedInstanceState.getBoolean("isSelectionMode", false);
                mGameAdapter = new GameAdapter(this, games);
                if (isSelectionMode) {
                    List<Integer> selectedItems = savedInstanceState.getIntegerArrayList("selectedItems");
                    mGameAdapter.setSelectedItems(selectedItems);
                    mGameAdapter.setSelectionMode(true);
                    mGameAdapter.notifyDataSetChanged();
                    mActionMode = startSupportActionMode(this);
                    setTitleActionMode(mGameAdapter.getSelectedItemCount());
                }
            }
        }
    }

    private void init() {
        _rvGames = findViewById(R.id.rv_main_games);
        _tvErrorMessage = findViewById(R.id.tv_main_error_message);
        _fabAdd = findViewById(R.id.fab_main_add_games);
        config();
    }

    private void config() {
        listGames();
    }

    private void configRecyclerView(List<Game> games) {
        if (mGameAdapter == null)
            mGameAdapter = new GameAdapter(this, games);
        mGameAdapter.setOnItemClickListener(this);
        mGameAdapter.setOnItemLongClickListener(this);
        if (_rvGames.getItemDecorationCount() == 0)
            _rvGames.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        _rvGames.setAdapter(mGameAdapter);
    }

    private void listGames() {
        mGameRepository = InstantiateUtil.instantialeGameRepository(this);
        mGameRepository.list(new GameDataSource.OnGamesListedCallback() {
            @Override
            public void onSuccess(List<Game> games) {
                _rvGames.setVisibility(View.VISIBLE);
                _tvErrorMessage.setVisibility(View.GONE);
                configRecyclerView(games);
            }

            @Override
            public void onFailure() {
                _rvGames.setVisibility(View.GONE);
                _tvErrorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void deleteGames(ActionMode mode) {
        List<Integer> selecionados = mGameAdapter.getSelectedItems();
        if (selecionados.size() > 0) {
            mGameRepository.delete(MainActivity.this, mGameAdapter.getSelectedGames(), new Callbacks.OnLocalCallback() {
                @Override
                public void onSuccess(String message) {
                    for (int i = (selecionados.size() - 1); i >= 0; i--) {
                        mGameAdapter.removeItem(selecionados.get(i));
                    }
                    mode.finish();
                    if (mGameAdapter.getItemCount() == 0) {
                        _rvGames.setVisibility(View.GONE);
                        _tvErrorMessage.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(_fabAdd, getResources().getQuantityString(R.plurals.message_games_deleted,
                            selecionados.size(), selecionados.size()), Snackbar.LENGTH_SHORT)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
                }

                @Override
                public void onFailure(String message) {
                    Snackbar.make(_fabAdd, message, Snackbar.LENGTH_LONG)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
                }
            });
        } else
            Snackbar.make(_fabAdd, R.string.message_no_game_select, Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
    }

    private void selectGame(int position) {
        mGameAdapter.select(position);
        setTitleActionMode(mGameAdapter.getSelectedItemCount());
    }

    private void setTitleActionMode(int selectedItemsCount) {
        if (mActionMode != null)
            mActionMode.setTitle(getResources().getQuantityString(R.plurals.text_selected_games,
                    selectedItemsCount, selectedItemsCount));
    }

    public void openAddGame(View v) {
        if (mActionMode != null)
            mActionMode.finish();
        startActivityForResult(new Intent(getApplicationContext(), AddGameActivity.class), CODE_OPERATION_SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_OPERATION_SUCCESS) {
            if (resultCode == RESULT_OK) {
                mGameAdapter = null;
                listGames();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(View view, int position, Object item) {
        if (mActionMode == null) {
            Game game = (Game) item;
            Intent intent = new Intent(this, AddGameActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("game", game);
            startActivityForResult(intent, CODE_OPERATION_SUCCESS);
        } else
            selectGame(position);
    }

    @Override
    public void onItemLongClickListener(View view, int position, Object item) {
        if (mActionMode == null) {
            mActionMode = startSupportActionMode(this);
            selectGame(position);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_action_main, menu);
        mGameAdapter.showChecks(true);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_select_all:
                mGameAdapter.selectAll(mGameAdapter.getItemCount() == mGameAdapter.getSelectedItemCount());
                setTitleActionMode(mGameAdapter.getSelectedItemCount());
                return true;
            case R.id.action_main_delete:
                new AlertDialog.Builder(MainActivity.this).setTitle(R.string.title_alert_aviso)
                        .setMessage(getResources().getQuantityString(R.plurals.message_warning_delete_game,
                                mGameAdapter.getSelectedItemCount(), mGameAdapter.getSelectedItemCount()))
                        .setPositiveButton(R.string.label_alert_button_sim, (dialog, which) -> deleteGames(mode))
                        .setNegativeButton(R.string.label_alert_button_nao, null).show();
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mGameAdapter.clearSelection();
        mActionMode = null;
    }

}
