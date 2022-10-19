package br.com.vicentec12.mygames.util

import android.view.View

typealias FunctionEmpty = () -> Unit
typealias FunctionParam<I> = (I) -> Unit
typealias FunctionReturn<O> = () -> O
typealias FunctionParamReturn<I, O> = (I) -> O

typealias OnItemClickListener = (View, Any?, Int) -> Unit
typealias OnItemLongClickListener = (View, Any?, Int) -> Unit