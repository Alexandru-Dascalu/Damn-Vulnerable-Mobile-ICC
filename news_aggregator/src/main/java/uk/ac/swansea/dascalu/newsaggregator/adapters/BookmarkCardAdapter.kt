package uk.ac.swansea.dascalu.newsaggregator.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.dfl.newsapi.model.ArticleDto
import com.squareup.picasso.Picasso

import uk.ac.swansea.dascalu.newsaggregator.Database
import uk.ac.swansea.dascalu.newsaggregator.FullArticleActivity
import uk.ac.swansea.dascalu.newsaggregator.R
import uk.ac.swansea.dascalu.newsaggregator.fragments.BookmarksFragment
import uk.ac.swansea.dascalu.newsaggregator.utils.getPublishTimeAgo

class BookmarkCardAdapter(private var articleList: List<ArticleDto>,
                          private val bookmarksFragment: BookmarksFragment
) : RecyclerView.Adapter<BookmarkCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.article_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]

        val reason = Database.instance.getKeywordForArticle(article)
        if(reason != "") {
            holder.displayReason.text = bookmarksFragment.context!!.getString(
                R.string.article_card_reason_message, reason)
        } else {
            holder.displayReason.text = ""
        }

        holder.articleTitle.text = article.title
        holder.articleDescription.text = article.description
        holder.articleSource.text = article.source.name
        holder.publicationTime.text = "• ${getPublishTimeAgo(article.publishedAt)}"
        holder.article = article

        Picasso.get().load(article.urlToImage).into(holder.articleImage)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val displayReason: AppCompatTextView = itemView.findViewById<AppCompatTextView>(R.id.displayReason)
        val articleImage: AppCompatImageView = itemView.findViewById<AppCompatImageView>(R.id.articleImage)
        val articleTitle: AppCompatTextView = itemView.findViewById<AppCompatTextView>(R.id.articleTitle)
        val articleDescription: AppCompatTextView = itemView.findViewById<AppCompatTextView>(R.id.articleDescription)
        val articleSource: AppCompatTextView = itemView.findViewById<AppCompatTextView>(R.id.articleSource)
        val publicationTime: AppCompatTextView = itemView.findViewById<AppCompatTextView>(R.id.publicationTime)
        val bookmarkButton: AppCompatImageButton = itemView.findViewById<AppCompatImageButton>(R.id.bookmarkImageButton)

        val cardView : CardView = itemView.findViewById<CardView>(R.id.card_view)
        var article: ArticleDto? = null

        init {
            cardView.setOnClickListener { _ ->
                if(article != null) {
                    val displayArticleIntent = Intent(bookmarksFragment.context,
                        FullArticleActivity::class.java)
                    displayArticleIntent.putExtra("LINK", article!!.url)

                    bookmarksFragment.context!!.startActivity(displayArticleIntent)
                }
            }

            bookmarkButton.visibility = View.INVISIBLE
            bookmarkButton.isClickable = false
        }
    }
}