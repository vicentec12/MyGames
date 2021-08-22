package br.com.vicentec12.mygames.data.mapper

import br.com.vicentec12.mygames.data.local.entities.GameEntity
import br.com.vicentec12.mygames.domain.model.Game

fun GameEntity.toModel() = Game(id, name, year, consoleId)

fun List<GameEntity>.toModelList() = map { it.toModel() } as ArrayList

fun Game.toEntity() = GameEntity(id, name, year, consoleId)

fun List<Game>.toEntityList() = map { it.toEntity() } as ArrayList