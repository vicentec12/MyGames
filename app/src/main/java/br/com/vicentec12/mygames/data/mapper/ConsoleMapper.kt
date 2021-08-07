package br.com.vicentec12.mygames.data.mapper

import br.com.vicentec12.mygames.data.local.entities.ConsoleEntity
import br.com.vicentec12.mygames.domain.model.Console

fun ConsoleEntity.toModel() = Console(id, name, image)

fun List<ConsoleEntity>.toModelList() = map { it.toModel() } as ArrayList

fun Console.toEntity() = ConsoleEntity(id, name, image)

fun List<Console>.toEntityList() = map { it.toEntity() } as ArrayList