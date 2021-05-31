package br.com.vicentec12.mygames.interfaces

import android.view.View

fun interface OnItemClickListener {

    fun onItemClick(mView: View, mItem: Any?, mPosition: Int)

}