package br.com.vicentec12.mygames.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _appBarTitle = MutableLiveData("")
    val appBarTitle: LiveData<String> = _appBarTitle

    private val _isShownAppBarNavigationIcon = MutableLiveData(false)
    val isShownAppBarNavigationIcon: LiveData<Boolean> = _isShownAppBarNavigationIcon

    fun configAppBar(mTitle: String?, mIsShownNavigateIcon: Boolean?) {
        _appBarTitle.value = mTitle
        _isShownAppBarNavigationIcon.value = mIsShownNavigateIcon
    }

}