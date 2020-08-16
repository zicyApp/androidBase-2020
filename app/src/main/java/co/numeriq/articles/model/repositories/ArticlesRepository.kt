package co.numeriq.articles.model.repositories

import co.numeriq.articles.api.ApiInterface
import co.numeriq.articles.model.PagedResponse
import io.reactivex.Single

class ArticlesRepository(private val api: ApiInterface)  {
    fun getArticles() : Single<PagedResponse> {
        return api.getArticles()
    }
}