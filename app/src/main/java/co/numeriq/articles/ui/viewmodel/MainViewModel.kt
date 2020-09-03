package co.numeriq.articles.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import co.numeriq.articles.base.RxViewModel
import co.numeriq.articles.extensions.with
import co.numeriq.articles.model.Article
import co.numeriq.articles.model.repositories.ArticlesRepository
import co.numeriq.articles.ui.viewmodel.state.*

class MainViewModel(private val repo: ArticlesRepository) : RxViewModel() {
    private val _uiState = MutableLiveData<UIState>().apply {
        value = DefaultState
    }

    val posts: MutableLiveData<List<Article>> = MutableLiveData()
    var loader: MutableLiveData<Boolean> = MutableLiveData()
    val uiState: LiveData<UIState>
        get() = _uiState

    init {
        loader.postValue(true)
    }

    fun fetchArticles() {
        addToDisposable(
            repo.getArticles().with()
                .doOnSubscribe { handleLoadingState() }
                .doOnSuccess { handlePostList(it.articles ?: listOf()) }
                .doOnError { handleFailure(it) }
                .subscribe(
                    { items -> handlePostList(items.articles ?: listOf()) },
                    { throwable -> handleFailure(throwable) })
        )
    }

    private fun handleLoadingState() {
        _uiState.value = LoadingState
    }

    private fun handlePostList(articles: List<Article>) {
        loader.postValue(false)
        posts.value = articles
        _uiState.value = RetrievedArticleState(listOf<String>())
    }

    private fun handleFailure(t: Throwable) {
        loader.postValue(false)
        _uiState.value =
            ErrorState(
                t.message.toString()
            )
    }

    var isLoading = Transformations.map(loader) {
        it
    }
}