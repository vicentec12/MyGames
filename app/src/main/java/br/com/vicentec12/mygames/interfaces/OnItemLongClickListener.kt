package br.com.vicentec12.mygames.interfaces

import android.view.View

fun interface OnItemLongClickListener {

    operator fun invoke(mView: View, mItem: Any?, mPosition: Int)

}