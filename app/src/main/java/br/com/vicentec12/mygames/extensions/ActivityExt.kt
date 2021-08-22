package br.com.vicentec12.mygames.extensions

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> Activity.viewBinding(crossinline mBindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) { mBindingInflater.invoke(layoutInflater) }