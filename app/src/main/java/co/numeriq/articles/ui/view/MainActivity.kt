package co.numeriq.articles.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import co.numeriq.articles.R
import co.numeriq.articles.base.BindingActivity
import co.numeriq.articles.databinding.ActivityMainBinding
import co.numeriq.articles.ui.viewmodel.MainViewModel
import androidx.lifecycle.Observer
import co.numeriq.articles.ui.viewmodel.state.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BindingActivity<ActivityMainBinding>(), ArticlesAdapter.OnItemClickListener {

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_main

    private val adapter =
        ArticlesAdapter(arrayListOf(), this)
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
        observe()
    }

    private fun bind() {
        binding.viewModel = getViewModel()
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.articleRecyclerView.adapter = adapter
    }

    private fun observe() {
        viewModel.uiState.observe(this, stateUI)
        viewModel.fetchArticles()
    }

    private val stateUI = Observer<UIState> { state ->
        state?.let {
            when (state) {
                is DefaultState -> { }
                is LoadingState -> { }
                is ErrorState -> {
                    Toast.makeText(this, state.errorMessage, Toast.LENGTH_LONG).show()
                }
                is RetrievedArticleState -> {
                    adapter.replaceData(state.articleList)
                }

            }
        }
    }

    override fun onItemClick(position: Int) {
    }
}