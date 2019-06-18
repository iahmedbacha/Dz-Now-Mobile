package com.dznow.recyclers

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dznow.R
import com.dznow.activities.ArticleActivity
import com.dznow.models.ArticleModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_article_preview.view.*
import com.dznow.utils.timeSince
import kotlin.collections.ArrayList

class ArticlePreviewAdapter(private val articles: ArrayList<ArticleModel>) :
    RecyclerView.Adapter<ArticlePreviewAdapter.ArticlePreviewHolder>() {

    // creating the layout for the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlePreviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_article_preview, parent, false)
        return ArticlePreviewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    // passing data to the view Holder
    override fun onBindViewHolder(holder: ArticlePreviewHolder, position: Int) {
        val article = articles[position]
        holder.sourceName.text = article.source?.name
        // getting string that describes how much time ago since the creation of the article
        holder.createdAt.text = timeSince(article.created_at)
        holder.title.text = article.title
        holder.minutesRead.text = article.minutes_read.toString()
        Picasso.get().load(article.cover_url).into(holder.articleCover)
        holder.article = article
    }

    // Article Preview Holder
    inner class ArticlePreviewHolder(itemView: View, var article: ArticleModel? = null) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val sourceName: TextView
        val createdAt: TextView
        val title: TextView
        val minutesRead: TextView
        val articleCover: ImageView

        init {
            itemView.setOnClickListener(this)
            sourceName = itemView.textViewArticleSource
            createdAt = itemView.textViewArticleTimeSince
            title = itemView.textViewArticleTitle
            minutesRead = itemView.textViewArticleMinutesRead
            articleCover = itemView.imageViewArticleCover
        }

        override fun onClick(view: View) {
            val intent = Intent(view.context, ArticleActivity::class.java)
            intent.putExtra("sourceName", article?.source?.name)
            intent.putExtra("title", article?.title)
            intent.putExtra("content", article?.content)
            intent.putExtra("minutes_read", article?.minutes_read)
            intent.putExtra("cover_url", article?.cover_url)
            intent.putExtra("created_at", article?.created_at)
            intent.putExtra("url", article?.url)
            view.context.startActivity(intent)
        }
    }
}
