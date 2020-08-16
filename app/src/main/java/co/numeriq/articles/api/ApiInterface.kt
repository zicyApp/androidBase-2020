package co.numeriq.articles.api

import co.numeriq.articles.model.PagedResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {
    @GET("/v2/everything?q=bitcoin&from=2020-08-15&sortBy=publishedAt&apiKey=543569779e4d49778b69d611b3b65dc1")
    fun getArticles(): Single<PagedResponse>
}