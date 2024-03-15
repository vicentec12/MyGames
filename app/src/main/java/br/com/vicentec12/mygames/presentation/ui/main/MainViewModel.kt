package br.com.vicentec12.mygames.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.extensions.EMPTY
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _appBarTitle = MutableLiveData(String.EMPTY)
    val mAppBarTitle: LiveData<String> = _appBarTitle

    private val _isShownAppBarNavigationIcon = MutableLiveData(false)
    val mIsShownAppBarNavigationIcon: LiveData<Boolean> = _isShownAppBarNavigationIcon

    private var _mSelectedConsole: Console? = null
    var mSelectedConsole: Console?
        get() = _mSelectedConsole
        set(value) {
            _mSelectedConsole = value
        }

    private var _mSelectedGame: Game? = null
    var mSelectedGame: Game?
        get() = _mSelectedGame
        set(value) {
            _mSelectedGame = value
        }

    fun configAppBar(mTitle: String?, mIsShownNavigateIcon: Boolean?) {
        _appBarTitle.value = mTitle
        _isShownAppBarNavigationIcon.value = mIsShownNavigateIcon
    }
}