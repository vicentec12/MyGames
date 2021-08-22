package br.com.vicentec12.mygames.extensions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar

inline fun <T : ViewBinding> Fragment.viewBinding(crossinline bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater, view?.parent as? ViewGroup, false)
    }

inline fun Fragment.withArgs(argsBundle: Bundle.() -> Unit) =
    apply { arguments = Bundle().apply(argsBundle) }

inline fun <reified T> Fragment.arguments(key: String, fallback: T): Lazy<T> =
    lazy { this.arguments?.get(key) as? T ?: fallback }

fun Fragment.makeSnackbar(@StringRes message: Int) = makeSnackbar(getString(message))

fun Fragment.makeSnackbar(message: String) = Snackbar.make(requireView(), message, LENGTH_LONG)
    .setAnimationMode(ANIMATION_MODE_SLIDE)