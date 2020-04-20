package br.com.vicentec12.mygames.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.vicentec12.mygames.R;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.GameDataSource;
import br.com.vicentec12.mygames.data.source.repository.GameRepository;
import br.com.vicentec12.mygames.interfaces.OnItemClickListener;
import br.com.vicentec12.mygames.ui.adapter.GameAdapter;
import br.com.vicentec12.mygames.util.InstantiateUtil;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView _rvGames;
    private AppCompatTextView _tvErrorMessage;
    private FloatingActionButton _fabAdd;

    private GameAdapter mGameAdapter;
    private GameRepository mGameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listGames();
    }

    private void init() {
        _rvGames = findViewById(R.id.rv_main_games);
        _tvErrorMessage = findViewById(R.id.tv_main_error_message);
        _fabAdd = findViewById(R.id.fab_main_add_games);
        config();
    }

    private void config() {

    }

    private void configRecyclerView(List<Game> games) {
        mGameAdapter = new GameAdapter(this, games);
        mGameAdapter.setOnItemClickListener(this);
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

    public void openAddGame(View v) {
        startActivity(new Intent(this, AddGameActivity.class));
    }

    @Override
    public void onItemClickListener(View view, int position, Object item) {
        Game game = (Game) item;
        Toast.makeText(this, game.getName(), Toast.LENGTH_SHORT).show();
    }

}
