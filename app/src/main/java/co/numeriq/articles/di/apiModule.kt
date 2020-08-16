package co.numeriq.articles.di

import co.numeriq.articles.api.ApiInterface
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(ApiInterface::class.java) }
}