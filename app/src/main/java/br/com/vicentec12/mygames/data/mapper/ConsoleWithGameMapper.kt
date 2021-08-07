package br.com.vicentec12.mygames.data.mapper

import br.com.vicentec12.mygames.data.local.entities.ConsoleWithGamesEntity
import br.com.vicentec12.mygames.domain.model.Console

fun ConsoleWithGamesEntity.toModel() =
    Console(console.id, console.name, console.image, games.toModelList())

fun List<ConsoleWithGamesEntity>.toModelList() = map { it.toModel() } as ArrayList

fun Console.toWithGamesEntity() =
    ConsoleWithGamesEntity(console = toEntity(), games = games?.toEntityList() ?: arrayListOf())

fun List<Console>.toWithGamesEntityList() = map { it.toWithGamesEntity() } as ArrayList