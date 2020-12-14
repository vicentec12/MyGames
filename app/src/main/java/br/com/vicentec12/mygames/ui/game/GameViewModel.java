package br.com.vicentec12.mygames.ui.game;

import android.util.SparseBooleanArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import br.com.vicentec12.mygames.data.model.Game;
import br.com.vicentec12.mygames.data.source.game.GameDataSource;
import br.com.vicentec12.mygames.data.source.game.GameRepository;
import br.com.vicentec12.mygames.extensions.Event;

public class GameViewModel extends ViewModel {

    private final int CHILD_RECYCLER = 0;
    private final int CHILD_TEXT = 1;

    private final GameRepository mGameRepository;

    // Activity
    private final MutableLiveData<List<Game>> mMutableGames = new MutableLiveData<>();
    private final MutableLiveData<Integer> mMutableViewFlipperChild = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> mMutableMessage = new MutableLiveData<>();
    private final MutableLiveData<Event<List<Integer>>> mMutablePluralMessage = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> mMutableHasActionModeFinish = new MutableLiveData<>();

    // Adapter
    private final MutableLiveData<Boolean> mMutableSelectionMode = new MutableLiveData<>();
    private final MutableLiveData<SparseBooleanArray> mMutableSelectedItems = new MutableLiveData<>();

    public GameViewModel(GameRepository mGameRepository) {
        this.mGameRepository = mGameRepository;
    }

    // Activity

    private List<Integer> createPluralMessage(int mMessage, int mQuantity) {
        List<Integer> mPluralMessage = new ArrayList<>();
        mPluralMessage.add(mMessage);
        mPluralMessage.add(mQuantity);
        return mPluralMessage;
    }

    public void listSavedGames() {
        mGameRepository.list(new GameDataSource.OnGamesListedCallback() {
            @Override
            public void onSuccess(List<Game> games) {
                mMutableViewFlipperChild.setValue(CHILD_RECYCLER);
                mMutableGames.setValue(games);
            }

            @Override
            public void onFailure() {
                mMutableViewFlipperChild.setValue(CHILD_TEXT);
            }
        });
    }

    public void deleteGames() {
        mGameRepository.delete(getSelectedGames(), new GameDataSource.OnGameDeletedCallback() {
            @Override
            public void onSuccess(int mMessage, int mNumDeleted) {
                List<Game> games = mMutableGames.getValue();
                if (games != null) {
                    List<Integer> selecionados = getSelectedItemsPosition();
                    for (int i = (selecionados.size() - 1); i >= 0; i--) {
                        int position = selecionados.get(i);
                        games.remove(position);
                    }
                    mMutableGames.setValue(games);
                    if (games.size() == 0) mMutableViewFlipperChild.setValue(CHILD_TEXT);
                    mMutablePluralMessage.setValue(new Event<>(createPluralMessage(mMessage, mNumDeleted)));
                    mMutableHasActionModeFinish.setValue(new Event<>(true));
                }
            }

            @Override
            public void onFailure(int mMessage) {
                mMutableMessage.setValue(new Event<>(mMessage));
            }
        });
    }

    public MutableLiveData<List<Game>> getMutableGames() {
        return mMutableGames;
    }

    public MutableLiveData<Integer> getMutableViewFlipperChild() {
        return mMutableViewFlipperChild;
    }

    public MutableLiveData<Event<Integer>> getMutableMessage() {
        return mMutableMessage;
    }

    public MutableLiveData<Event<List<Integer>>> getMutablePluralMessage() {
        return mMutablePluralMessage;
    }

    public MutableLiveData<Event<Boolean>> getMutableHasActionModeFinish() {
        return mMutableHasActionModeFinish;
    }

    // Adapter

    public void clearSelection() {
        SparseBooleanArray selections = mMutableSelectedItems.getValue();
        if (selections != null)
            selections.clear();
        mMutableSelectedItems.setValue(selections);
    }

    public void showChecks(boolean isSelectionModeVisible) {
        mMutableSelectionMode.setValue(isSelectionModeVisible);
    }

    public void select(int position) {
        if (mMutableSelectedItems.getValue() == null)
            mMutableSelectedItems.setValue(new SparseBooleanArray());
        if (mMutableSelectedItems.getValue() != null) {
            SparseBooleanArray selections = mMutableSelectedItems.getValue();
            if (selections.get(position, false))
                selections.delete(position);
            else
                selections.put(position, true);
            mMutableSelectedItems.setValue(selections);
        }
    }

    public void selectAll(boolean hasSelectAll) {
        SparseBooleanArray selections = mMutableSelectedItems.getValue();
        List<Game> games = mMutableGames.getValue();
        if (selections != null && games != null) {
            if (!hasSelectAll) selections.clear();
            for (int i = 0; i < games.size(); i++) {
                if (selections.get(i, false))
                    selections.delete(i);
                else
                    selections.put(i, true);
            }
            mMutableSelectedItems.setValue(selections);
        }
    }

    public List<Integer> getSelectedItemsPosition() {
        List<Integer> items = new ArrayList<>();
        if (mMutableSelectedItems.getValue() != null) {
            for (int i = 0; i < mMutableSelectedItems.getValue().size(); i++) {
                items.add(mMutableSelectedItems.getValue().keyAt(i));
            }
        }
        return items;
    }

    public List<Game> getSelectedGames() {
        List<Game> selectedGames = new ArrayList<>();
        if (mMutableSelectedItems.getValue() != null && mMutableGames.getValue() != null) {
            for (int i = 0; i < mMutableSelectedItems.getValue().size(); i++) {
                selectedGames.add(mMutableGames.getValue().get(mMutableSelectedItems.getValue().keyAt(i)));
            }
        }
        return selectedGames;
    }

    public int getSelectedItemCount() {
        if (mMutableSelectedItems.getValue() != null)
            return mMutableSelectedItems.getValue().size();
        return 0;
    }

    public boolean isSelectionModeVisible() {
        if (mMutableSelectionMode.getValue() != null)
            return mMutableSelectionMode.getValue();
        return false;
    }

    public boolean isGameSelected(int position) {
        if (mMutableSelectedItems.getValue() != null)
            return mMutableSelectedItems.getValue().get(position, false);
        return false;
    }

    public MutableLiveData<Boolean> getMutableSelectionMode() {
        return mMutableSelectionMode;
    }

    public MutableLiveData<SparseBooleanArray> getMutableSelectedItems() {
        return mMutableSelectedItems;
    }

    public static class GameViewModelFactory implements ViewModelProvider.Factory {

        private final GameRepository mGameRepository;

        public GameViewModelFactory(GameRepository mGameRepository) {
            this.mGameRepository = mGameRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new GameViewModel(mGameRepository);
        }
    }

}
