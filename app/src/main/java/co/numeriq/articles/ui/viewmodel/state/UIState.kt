package co.numeriq.articles.ui.viewmodel.state

import co.numeriq.articles.ui.model.ArticleUI

sealed class UIState

object DefaultState : UIState()
object LoadingState : UIState()
data class ErrorState(internal val errorMessage: String) : UIState()
data class RetrievedArticleState(internal val articleList: List<ArticleUI>) : UIState()