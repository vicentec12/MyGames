package br.com.vicentec12.mygames.ui.console;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import br.com.vicentec12.mygames.data.model.ConsoleWithGames;
import br.com.vicentec12.mygames.databinding.ActivityConsoleBinding;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.ui.add_game.AddGameActivity;
import br.com.vicentec12.mygames.ui.game.GameActivity;
import br.com.vicentec12.mygames.util.InstantiateUtil;

public class ConsoleActivity extends AppCompatActivity implements OnItemClickListener {

    public static final int CODE_OPERATION_SUCCESS = 2907;

    private ActivityConsoleBinding mBinding;

    private ConsoleViewModel mViewModel;
    private ConsoleAdapter mAdapter;

    public static Intent newIntentInstance(Context context) {
        return new Intent(context, ConsoleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityConsoleBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    private void init() {
        setupViewModel();
        setupToolbar();
        setupAdapter();
        mViewModel.listConsoles();
    }

    private void setupViewModel() {
        ConsoleViewModelFactory mFactory =
                new ConsoleViewModelFactory(InstantiateUtil.initConsoleRepository(this));
        mViewModel = ViewModelProviders.of(this, mFactory).get(ConsoleViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }

    private void setupToolbar() {
        setSupportActionBar(mBinding.lytToolbar.toolbar);
    }

    private void setupAdapter() {
        mAdapter = new ConsoleAdapter();
        mAdapter.setOnItemClickListener(this);
        mBinding.rvwConsole.setAdapter(mAdapter);
    }

    public void openAddGame(View v) {
        startActivityForResult(AddGameActivity.newIntentInstance(this, null),
                CODE_OPERATION_SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_OPERATION_SUCCESS) {
            if (resultCode == RESULT_OK)
                mViewModel.listConsoles();
        }
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        ConsoleWithGames consoleWithGames = (ConsoleWithGames) item;
        startActivityForResult(GameActivity.newIntentInstance(this, consoleWithGames.getConsole()),
                CODE_OPERATION_SUCCESS);
    }

}