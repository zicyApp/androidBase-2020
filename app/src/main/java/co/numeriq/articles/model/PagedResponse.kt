package co.numeriq.articles.model

class PagedResponse (
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article>? = null
)