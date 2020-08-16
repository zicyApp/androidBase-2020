package co.numeriq.articles.di

import co.numeriq.articles.model.repositories.ArticlesRepository
import org.koin.dsl.module

val repoModule = module {
    single { ArticlesRepository(get()) }
}