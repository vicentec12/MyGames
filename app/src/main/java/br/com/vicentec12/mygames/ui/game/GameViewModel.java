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
    private final MutableLiveData<List<Game>> mGamesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Console> mConsoleLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mOrderByLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mViewFlipperLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> mMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event<List<Integer>>> mPluralMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> mHasActionModeFinishLiveData = new MutableLiveData<>();

    // Adapter
    private final MutableLiveData<Boolean> mMutableSelectionMode = new MutableLiveData<>();
    private final MutableLiveData<SparseBooleanArray> mMutableSelectedItems = new MutableLiveData<>();

    public GameViewModel(GameRepository mGameRepository) {
        this.mGameRepository = mGameRepository;
    }

    // Activity

    public void setConsole(Console console) {
        mConsoleLiveData.setValue(console);
    }

    public int getOrderBySelection() {
        if (mOrderByLiveData.getValue() != null)
            return mOrderByLiveData.getValue();
        return GameLocalDataSource.ORDER_BY_NAME;
    }

    public void listSavedGames() {
        mViewFlipperLiveData.setValue(CHILD_PROGRESS);
        mGameRepository.list(mConsoleLiveData.getValue(), getOrderBySelection(), new GameDataSource.OnGamesListedCallback() {
            @Override
            public void onSuccess(List<Game> games) {
                mViewFlipperLiveData.setValue(CHILD_RECYCLER);
                mGamesLiveData.setValue(games);
            }

            @Override
            public void onFailure() {
                mViewFlipperLiveData.setValue(CHILD_TEXT);
            }
        });
    }

    public void deleteGames() {
        mGameRepository.delete(getSelectedGames(), new GameDataSource.OnGameDeletedCallback() {
            @Override
            public void onSuccess(int mMessage, int mNumDeleted) {
                List<Game> games = mGamesLiveData.getValue();
                if (games != null) {
                    List<Integer> selecionados = getSelectedItemsPosition();
                    for (int i = (selecionados.size() - 1); i >= 0; i--) {
                        int position = selecionados.get(i);
                        games.remove(position);
                    }
                    mGamesLiveData.setValue(games);
                    if (games.size() == 0) mViewFlipperLiveData.setValue(CHILD_TEXT);
                    mPluralMessageLiveData.setValue(new Event<>(createPluralMessage(mMessage, mNumDeleted)));
                    mHasActionModeFinishLiveData.setValue(new Event<>(true));
                }
            }

            @Override
            public void onFailure(int mMessage) {
                mMessageLiveData.setValue(new Event<>(mMessage));
            }
        });
    }

    private List<Integer> createPluralMessage(int mMessage, int mQuantity) {
        List<Integer> mPluralMessage = new ArrayList<>();
        mPluralMessage.add(mMessage);
        mPluralMessage.add(mQuantity);
        return mPluralMessage;
    }

    public MutableLiveData<List<Game>> getGamesLiveData() {
        return mGamesLiveData;
    }

    public MutableLiveData<Integer> getOrderByLiveData() {
        return mOrderByLiveData;
    }

    public MutableLiveData<Integer> getViewFlipperLiveData() {
        return mViewFlipperLiveData;
    }

    public MutableLiveData<Event<Integer>> getMessageLiveData() {
        return mMessageLiveData;
    }

    public MutableLiveData<Event<List<Integer>>> getPluralLiveData() {
        return mPluralMessageLiveData;
    }

    public MutableLiveData<Event<Boolean>> getHasActionModeFinishMutable() {
        return mHasActionModeFinishLiveData;
    }

    public MutableLiveData<Console> getConsoleLiveData() {
        return mConsoleLiveData;
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
        List<Game> games = mGamesLiveData.getValue();
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
        if (mMutableSelectedItems.getValue() != null && mGamesLiveData.getValue() != null) {
            for (int i = 0; i < mMutableSelectedItems.getValue().size(); i++) {
                selectedGames.add(mGamesLiveData.getValue().get(mMutableSelectedItems.getValue().keyAt(i)));
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

    public MutableLiveData<Boolean> getSelectionModeLiveData() {
        return mMutableSelectionMode;
    }

    public MutableLiveData<SparseBooleanArray> getSelectedItemsLiveData() {
        return mMutableSelectedItems;
    }

}
