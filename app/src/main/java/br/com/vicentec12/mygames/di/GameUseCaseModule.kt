package br.com.vicentec12.mygames.di

import br.com.vicentec12.mygames.domain.use_case.game.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class GameUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindListGamesUseCase(mUseCase: ListGamesUseCaseImpl): ListGamesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindInsertGameUseCase(mUseCase: InsertGameUseCaseImpl): InsertGameUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindUpdateGameUseCase(mUseCase: UpdateGameUseCaseImpl): UpdateGameUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDeleteGamesUseCase(mUseCase: DeleteGamesUseCaseImpl): DeleteGamesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDeleteAllGamesUseCase(mUseCase: DeleteAllGamesUseCaseImpl): DeleteAllGamesUseCase

}