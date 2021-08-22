package br.com.vicentec12.mygames.interfaces

import android.view.View

fun interface OnItemClickListener {

    operator fun invoke(mView: View, mItem: Any?, mPosition: Int)

}