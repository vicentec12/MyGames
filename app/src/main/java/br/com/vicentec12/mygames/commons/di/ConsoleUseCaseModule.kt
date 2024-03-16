package br.com.vicentec12.mygames.commons.di

import br.com.vicentec12.mygames.domain.use_case.console.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ConsoleUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindListConsolesUseCase(mUseCase: ListConsolesUseCaseImpl): ListConsolesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindListWithGamesUseCase(mUseCase: ListWithGamesUseCaseImpl): ListWithGamesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindInsertAllConsolesUseCase(mUseCase: InsertAllConsolesUseCaseImpl): InsertAllConsolesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDeleteAllConsolesUseCase(mUseCase: DeleteAllConsolesUseCaseImpl): DeleteAllConsolesUseCase
}