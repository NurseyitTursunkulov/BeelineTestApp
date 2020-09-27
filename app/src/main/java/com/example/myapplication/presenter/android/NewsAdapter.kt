package com.example.myapplication.presenter.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.Article
import com.example.myapplication.R
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter : ListAdapter<Article, NewsAdapter.NewsItemViewHolder>(diffCallback){
    private lateinit var inflater: LayoutInflater
    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindNewsItem( item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        if (!this::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val itemView = inflater.inflate(R.layout.news_item, parent, false)
        return NewsItemViewHolder(itemView)
    }

    class NewsItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindNewsItem(article : Article) {
            itemView.textView.text = article.description
        }
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean =
                oldItem == newItem
        }
    }
}
