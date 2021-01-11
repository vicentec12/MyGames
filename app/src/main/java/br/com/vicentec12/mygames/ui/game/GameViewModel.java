package br.com.vicentec12.mygames.ui.game;

import android.util.SparseBooleanArray;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.vicentec12.mygames.data.model.Console;
import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.game.GameDataSource;
import br.com.vicentec12.mygames.data.source.game.GameLocalDataSource;
import br.com.vicentec12.mygames.data.source.game.GameRepository;
import br.com.vicentec12.mygames.extensions.Event;

public class GameViewModel extends ViewModel {

    private final int CHILD_PROGRESS = 0;
    private final int CHILD_RECYCLER = 1;
    private final int CHILD_TEXT = 2;

    private final GameRepository mGameRepository;

    // Activity
    private final MutableLiveData<List<Game>> _games = new MutableLiveData<>();
    private final MutableLiveData<Console> _console = new MutableLiveData<>();
    private final MutableLiveData<Integer> _orderBy = new MutableLiveData<>();
    private final MutableLiveData<Integer> _viewFlipper = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> _message = new MutableLiveData<>();
    private final MutableLiveData<Event<List<Integer>>> _pluralMessage = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> _hasActionModeFinish = new MutableLiveData<>();

    // Adapter
    private final MutableLiveData<Boolean> _selectionMode = new MutableLiveData<>();
    private final MutableLiveData<SparseBooleanArray> _selectedItems = new MutableLiveData<>();

    public GameViewModel(GameRepository mGameRepository) {
        this.mGameRepository = mGameRepository;
    }

    // Activity

    public void setConsole(Console console) {
        _console.setValue(console);
    }

    public int getOrderBySelection() {
        if (_orderBy.getValue() != null)
            return _orderBy.getValue();
        return GameLocalDataSource.ORDER_BY_NAME;
    }

    public void listSavedGames() {
        _viewFlipper.setValue(CHILD_PROGRESS);
        mGameRepository.list(_console.getValue(), getOrderBySelection(), new GameDataSource.OnGamesListedCallback() {
            @Override
            public void onSuccess(List<Game> games) {
                _viewFlipper.setValue(CHILD_RECYCLER);
                _games.setValue(games);
            }

            @Override
            public void onFailure() {
                _viewFlipper.setValue(CHILD_TEXT);
            }
        });
    }

    public void deleteGames() {
        mGameRepository.delete(getSelectedGames(), new GameDataSource.OnGameDeletedCallback() {
            @Override
            public void onSuccess(int mMessage, int mNumDeleted) {
                List<Game> games = _games.getValue();
                if (games != null) {
                    List<Integer> selecionados = getSelectedItemsPosition();
                    for (int i = (selecionados.size() - 1); i >= 0; i--) {
                        int position = selecionados.get(i);
                        games.remove(position);
                    }
                    _games.setValue(games);
                    if (games.size() == 0) _viewFlipper.setValue(CHILD_TEXT);
                    _pluralMessage.setValue(new Event<>(createPluralMessage(mMessage, mNumDeleted)));
                    _hasActionModeFinish.setValue(new Event<>(true));
                }
            }

            @Override
            public void onFailure(int mMessage) {
                _message.setValue(new Event<>(mMessage));
            }
        });
    }

    private List<Integer> createPluralMessage(int mMessage, int mQuantity) {
        List<Integer> mPluralMessage = new ArrayList<>();
        mPluralMessage.add(mMessage);
        mPluralMessage.add(mQuantity);
        return mPluralMessage;
    }

    public MutableLiveData<List<Game>> getGames() {
        return _games;
    }

    public MutableLiveData<Integer> getOrderBy() {
        return _orderBy;
    }

    public MutableLiveData<Integer> getViewFlipper() {
        return _viewFlipper;
    }

    public MutableLiveData<Event<Integer>> getMessage() {
        return _message;
    }

    public MutableLiveData<Event<List<Integer>>> getPlural() {
        return _pluralMessage;
    }

    public MutableLiveData<Event<Boolean>> getHasActionModeFinish() {
        return _hasActionModeFinish;
    }

    public MutableLiveData<Console> getConsole() {
        return _console;
    }

    // Adapter

    public void clearSelection() {
        SparseBooleanArray selections = _selectedItems.getValue();
        if (selections != null)
            selections.clear();
        _selectedItems.setValue(selections);
    }

    public void showChecks(boolean isSelectionModeVisible) {
        _selectionMode.setValue(isSelectionModeVisible);
    }

    public void select(int position) {
        if (_selectedItems.getValue() == null)
            _selectedItems.setValue(new SparseBooleanArray());
        if (_selectedItems.getValue() != null) {
            SparseBooleanArray selections = _selectedItems.getValue();
            if (selections.get(position, false))
                selections.delete(position);
            else
                selections.put(position, true);
            _selectedItems.setValue(selections);
        }
    }

    public void selectAll(boolean hasSelectAll) {
        SparseBooleanArray selections = _selectedItems.getValue();
        List<Game> games = _games.getValue();
        if (selections != null && games != null) {
            if (!hasSelectAll) selections.clear();
            for (int i = 0; i < games.size(); i++) {
                if (selections.get(i, false))
                    selections.delete(i);
                else
                    selections.put(i, true);
            }
            _selectedItems.setValue(selections);
        }
    }

    public List<Integer> getSelectedItemsPosition() {
        List<Integer> items = new ArrayList<>();
        if (_selectedItems.getValue() != null) {
            for (int i = 0; i < _selectedItems.getValue().size(); i++) {
                items.add(_selectedItems.getValue().keyAt(i));
            }
        }
        return items;
    }

    public List<Game> getSelectedGames() {
        List<Game> selectedGames = new ArrayList<>();
        if (_selectedItems.getValue() != null && _games.getValue() != null) {
            for (int i = 0; i < _selectedItems.getValue().size(); i++) {
                selectedGames.add(_games.getValue().get(_selectedItems.getValue().keyAt(i)));
            }
        }
        return selectedGames;
    }

    public int getSelectedItemCount() {
        if (_selectedItems.getValue() != null)
            return _selectedItems.getValue().size();
        return 0;
    }

    public boolean isSelectionModeVisible() {
        if (_selectionMode.getValue() != null)
            return _selectionMode.getValue();
        return false;
    }

    public boolean isGameSelected(int position) {
        if (_selectedItems.getValue() != null)
            return _selectedItems.getValue().get(position, false);
        return false;
    }

    public MutableLiveData<Boolean> getSelectionMode() {
        return _selectionMode;
    }

    public MutableLiveData<SparseBooleanArray> getSelectedItems() {
        return _selectedItems;
    }

}
