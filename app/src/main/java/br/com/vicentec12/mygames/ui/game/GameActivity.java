package br.com.vicentec12.mygames.ui.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.databinding.ActivityGameBinding;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.interfaces.OnItemLongClickListener;
import br.com.vicentec12.mygames.ui.add_game.AddGameActivity;
import br.com.vicentec12.mygames.util.InstantiateUtil;

public class GameActivity extends AppCompatActivity implements ActionMode.Callback, OnItemClickListener, OnItemLongClickListener {

    public static final int CODE_OPERATION_SUCCESS = 2907;
    private static final String EXTRA_CONSOLE = "console";

    private ActivityGameBinding mBinding;

    private ActionMode mActionMode;
    private GameAdapter mGameAdapter;
    private GameViewModel mViewModel;

    public static Intent newIntentInstance(Context context, Console console) {
        Intent mIntent = new Intent(context, GameActivity.class);
        mIntent.putExtra(EXTRA_CONSOLE, (Serializable) console);
        return mIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mViewModel.isSelectionModeVisible() && mActionMode == null)
            mActionMode = startSupportActionMode(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            String[] options = {getString(R.string.text_game_name), getString(R.string.text_game_year)};
            new AlertDialog.Builder(this).setTitle(R.string.title_alert_order_by)
                    .setItems(options, (dialog, which) -> {
                        mViewModel.getMutableOrderBySelection().setValue(which);
                        mViewModel.listSavedGames();
                    }).show();
            return true;
        } else if (id == R.id.action_about)
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        setupViewModel();
        setupToolbar();
        setupRecyclerView();
        setupSelectedItems();
        setupMessage();
        setupPluralMessage();
        setupHasActionModeFinish();
        Console mConsole = (Console) getIntent().getSerializableExtra(EXTRA_CONSOLE);
        mViewModel.setConsole(mConsole);
        mViewModel.listSavedGames();
    }

    private void setupViewModel() {
        GameViewModelFactory mFactory =
                new GameViewModelFactory(InstantiateUtil.initGameRepository(this));
        mViewModel = ViewModelProviders.of(this, mFactory).get(GameViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }

    private void setupToolbar() {
        setSupportActionBar(mBinding.lytToolbar.toolbar);
    }

    private void setupRecyclerView() {
        mGameAdapter = new GameAdapter(mViewModel);
        mGameAdapter.setHasStableIds(true); // Responsável por resolver glichs quando notifyDataSetChange era chamado
        mGameAdapter.setOnItemClickListener(this);
        mGameAdapter.setOnItemLongClickListener(this);
        if (mBinding.rvwGame.getItemDecorationCount() == 0)
            mBinding.rvwGame.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBinding.rvwGame.setAdapter(mGameAdapter);
        mBinding.rvwGame.setHasFixedSize(true);
    }

    private void setupSelectedItems() {
        mViewModel.getMutableSelectedItems().observe(this, integers -> {
            setTitleActionMode(integers.size());
            mBinding.rvwGame.post(() -> mGameAdapter.notifyDataSetChanged());
        });
    }

    private void setupMessage() {
        mViewModel.getMutableMessage().observe(this, integerEvent -> {
            Integer mMessage = integerEvent.getContentIfNotHandled();
            if (mMessage != null)
                Snackbar.make(mBinding.rvwGame, mMessage, BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
        });
    }

    private void setupPluralMessage() {
        mViewModel.getMutablePluralMessage().observe(this, integersEvent -> {
            List<Integer> mIntegersMessage = integersEvent.getContentIfNotHandled();
            if (mIntegersMessage != null) {
                String mMessage = getResources().getQuantityString(mIntegersMessage.get(0), mIntegersMessage.get(1),
                        mIntegersMessage.get(1));
                Snackbar.make(mBinding.rvwGame, mMessage, BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            }
        });
    }

    private void setupHasActionModeFinish() {
        mViewModel.getMutableHasActionModeFinish().observe(this, booleanEvent -> {
            Boolean hasFinished = booleanEvent.getContentIfNotHandled();
            if (hasFinished != null && hasFinished)
                mActionMode.finish();
        });
    }

    private void setTitleActionMode(int selectedItemsCount) {
        if (mActionMode != null)
            mActionMode.setTitle(getResources().getQuantityString(R.plurals.plural_selected_games,
                    selectedItemsCount, selectedItemsCount));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_OPERATION_SUCCESS) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                mViewModel.listSavedGames();
            }
        }
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        if (mActionMode == null) {
            Game mGame = (Game) item;
            startActivityForResult(AddGameActivity.newIntentInstance(this, mGame),
                    CODE_OPERATION_SUCCESS);
        } else
            mViewModel.select(position);
    }

    @Override
    public void onItemLongClick(View view, Object item, int position) {
        if (mActionMode == null)
            mActionMode = startSupportActionMode(this);
        mViewModel.select(position);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_action_main, menu);
        mViewModel.showChecks(true);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.action_main_select_all) {
            mViewModel.selectAll(mGameAdapter.getItemCount() == mViewModel.getSelectedItemCount());
            return true;
        } else if (item.getItemId() == R.id.action_main_delete) {
            int mSelectedItemCount = mViewModel.getSelectedItemCount();
            if (mSelectedItemCount > 0)
                new AlertDialog.Builder(GameActivity.this).setTitle(R.string.title_alert_warning)
                        .setMessage(getResources().getQuantityString(R.plurals.plural_message_warning_delete_game, mSelectedItemCount, mSelectedItemCount))
                        .setPositiveButton(R.string.label_alert_button_yes, (dialog, which) -> mViewModel.deleteGames())
                        .setNegativeButton(R.string.label_alert_button_no, null).show();
            else
                Snackbar.make(mBinding.rvwGame, R.string.message_no_game_select, BaseTransientBottomBar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mViewModel.clearSelection();
        mViewModel.showChecks(false);
        mActionMode = null;
    }

}
