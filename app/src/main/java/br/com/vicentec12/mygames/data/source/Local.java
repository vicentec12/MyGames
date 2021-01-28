package br.com.vicentec12.mygames.data.source;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Qualifier criado para diferenciar para o Dagger quando for preciso de uma instância
 * de um DataSource ele entregar um LocalDataSource.
 */
@Documented
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Local {
}
