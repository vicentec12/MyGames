package br.com.vicentec12.mygames.interfaces

import android.view.View

fun interface OnItemLongClickListener {

    fun onItemLongClick(mView: View, mItem: Any?, mPosition: Int)

}