package co.numeriq.articles.ui.viewmodel.state

sealed class UIState

object DefaultState : UIState()
object LoadingState : UIState()
data class ErrorState(internal val errorMessage: String) : UIState()
data class RetrievedArticleState(internal val articleList: List<String>) : UIState()