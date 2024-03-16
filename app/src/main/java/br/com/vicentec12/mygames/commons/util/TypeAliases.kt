package br.com.vicentec12.mygames.commons.util

typealias FunctionEmpty = () -> Unit
typealias FunctionParam<I> = (I) -> Unit
typealias FunctionReturn<O> = () -> O
typealias FunctionParamReturn<I, O> = (I) -> O

typealias OnItemClickListener<I> = (I?, Int) -> Unit
typealias OnItemLongClickListener<I> = (I?, Int) -> Unit