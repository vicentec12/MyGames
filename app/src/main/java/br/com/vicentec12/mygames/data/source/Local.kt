package br.com.vicentec12.mygames.data.source

import javax.inject.Qualifier

/**
 * Qualifier criado para diferenciar para o Dagger quando for preciso de uma instância
 * de um DataSource ele entregar um LocalDataSource.
 */
@MustBeDocumented
@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Local