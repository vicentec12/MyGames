package br.com.vicentec12.mygames.util

typealias FunctionEmpty = () -> Unit
typealias FunctionParam<I> = (I) -> Unit
typealias FunctionReturn<O> = () -> O
typealias FunctionParamReturn<I, O> = (I) -> O