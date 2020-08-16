package co.numeriq.articles.ui.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.numeriq.articles.databinding.ItemArticleBinding
import co.numeriq.articles.ui.model.ArticleUI
import com.bumptech.glide.Glide

class ArticleRecyclerViewAdapter(
    private var items: List<ArticleUI>?,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items?.get(position), listener)

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun replaceData(it: List<ArticleUI>) {
        items = it
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleUI?, listener: OnItemClickListener?) {
            binding.article = article
            if (listener != null) {
                binding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
            }

            binding.executePendingBindings()
        }
    }

}