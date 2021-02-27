package uk.ac.swansea.dascalu.newsaggregator.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.dfl.newsapi.model.ArticleDto
import com.squareup.picasso.Picasso

import uk.ac.swansea.dascalu.newsaggregator.model.Database
import uk.ac.swansea.dascalu.newsaggregator.FullArticleActivity
import uk.ac.swansea.dascalu.newsaggregator.NewsApiCallback
import uk.ac.swansea.dascalu.newsaggregator.R
import uk.ac.swansea.dascalu.newsaggregator.fragments.NewsStreamFragment

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class NewsCardAdapter(private var articleList: List<ArticleDto>,
                      private val newsStreamFragment: NewsStreamFragment)
    : RecyclerView.Adapter<NewsCardAdapter.ViewHolder>(), NewsApiCallback {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.article_layout, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        val reason = Database.instance.getKeywordForArticle(article)

        if(reason != "") {
            holder.displayReason.text = newsStreamFragment.context!!.getString(
                R.string.article_card_reason_message, reason)
        } else {
            holder.displayReason.text = ""
        }

        holder.articleTitle.text = article.title
        holder.articleDescription.text = article.description
        holder.articleSource.text = article.source.name
        holder.publicationTime.text = "• ${getPublishTimeAgo(article.publishedAt)}"

        if(Database.instance.isBookmarked(article)) {
            selectBookmark(holder.bookmarkButton)
        } else {
            unSelectBookmark(holder.bookmarkButton)
        }

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
                    val displayArticleIntent = Intent(newsStreamFragment.context,
                        FullArticleActivity::class.java)
                    displayArticleIntent.putExtra("LINK", article!!.url)

                    newsStreamFragment.context!!.startActivity(displayArticleIntent)
                }
            }

            bookmarkButton.setOnClickListener { view ->
                val imageButton = view as ImageButton

                if(Database.instance.isBookmarked(article!!)) {
                    unSelectBookmark(imageButton)
                    Database.instance.removeBookmarks(article!!)
                } else {
                    selectBookmark(imageButton)
                    Database.instance.addBookmark(article!!)
                }
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onGetArticles(articles: List<ArticleDto>) {
        if(newsStreamFragment.activity != null) {
            newsStreamFragment.activity!!.runOnUiThread {
                articleList = articles
                this.notifyDataSetChanged()
            }
        }
    }

    /**
     * Gets a nicely string describing when the article was published. If it was published less
     * than 7 days ago, the string will be 'x days ago' or 'x minutes ago'. It might be in another
     * language, depending on your Locale. If the article is more than 7 days old, it will display
     * the date, such 24 nov, 2020 .
     * @param publishingTime The publishing time of the article, in the yyyy-MM-dd'T'HH:mm:ss'Z' format.
     * @return a string representing the publishing time relative to the current time.
     */
    private fun getPublishTimeAgo(publishingTime: String) : String {
        try {
            val utcDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val publishTimeDate: Date = utcDateFormat.parse(publishingTime)!!

            val timeAgo: String = DateUtils.getRelativeTimeSpanString(
                publishTimeDate.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
            ).toString()

            return timeAgo
        } catch (e: ParseException) {
            return publishingTime
        }
    }

    private fun selectBookmark(imageButton: ImageButton) {
        imageButton.setImageResource(R.drawable.ic_baseline_bookmark_24)
    }

    private fun unSelectBookmark(imageButton: ImageButton) {
        imageButton.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
    }
}