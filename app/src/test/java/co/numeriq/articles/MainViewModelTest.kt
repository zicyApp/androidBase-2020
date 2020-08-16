package co.numeriq.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.numeriq.articles.model.Article
import co.numeriq.articles.model.PagedResponse
import co.numeriq.articles.model.Source
import co.numeriq.articles.model.repositories.ArticlesRepository
import co.numeriq.articles.ui.viewmodel.MainViewModel
import co.numeriq.articles.ui.viewmodel.state.DefaultState
import co.numeriq.articles.utils.*
import com.google.common.truth.Truth
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.Assert

import org.junit.Test
import org.junit.Rule

class MainViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private val repoMocked = mock<ArticlesRepository>()
    private val viewModel by lazy { MainViewModel(repoMocked) }
    private val networkError = "NetworkError"

    private val postList = listOf(
        Article(Source(), "John", "Post1Title", "Post1Body"),
        Article(Source(), "John", "Post2Title", "Post2Body")
    )
    private val pagedResponse = PagedResponse("ok", 2, postList)

    @Test
    fun loadArticles_response_okay() {
        whenever(repoMocked.getArticles()).thenReturn(Single.fromObservable(Observable.just(pagedResponse)))
        val liveDataUnderTest = testLiveDataPost()

        Truth.assert_()
            .that(liveDataUnderTest.observedValues)
            .isEqualTo(listOf<Article>())
        viewModel.fetchArticles()
        Assert.assertEquals(2, this.viewModel.posts.value?.count())

        val result = repoMocked.getArticles()
        val testObserver = TestObserver<PagedResponse>()

        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()

        result.doOnSubscribe {
            Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(listOf(postList))
        }

        result.doOnSuccess {
            Assert.assertEquals(false, viewModel.isLoading)
        }
    }


    @Test
    fun loadArticles_response_error() {
        whenever(repoMocked.getArticles()).thenReturn(Single.fromObservable(Observable.error(Error(networkError))))
        val liveDataUnderTest = viewModel.uiState.testObserver()

        Truth.assert_()
            .that(liveDataUnderTest.observedValues)
            .isEqualTo(listOf(DefaultState))
        viewModel.fetchArticles()
        Assert.assertNull(this.viewModel.posts.value)

        val result = repoMocked.getArticles()
        val testObserver = TestObserver<PagedResponse>()

        result.subscribe(testObserver)
        testObserver.assertErrorMessage(networkError)
    }


    private fun testLiveDataPost(): CustomTestObserver<List<Article>> {
        return viewModel.posts.testObserver()
    }
}