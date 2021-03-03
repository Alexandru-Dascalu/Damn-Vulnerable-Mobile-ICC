package uk.ac.swansea.dascalu.dvmicc.newsaggregator.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.dfl.newsapi.model.ArticleDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.R

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.adapters.NewsCardAdapter
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.utils.loadSecuritySettingsFromFile

class NewsBroadcastReceiver : BroadcastReceiver() {
    private val streamToAdapterMap : HashMap<String, NewsCardAdapter> = HashMap<String, NewsCardAdapter>()

    init {

    }

    override fun onReceive(context: Context?, intent: Intent) {
        if(intent.extras != null) {
            val jsonArticles = intent.getStringExtra("articles")
            val newsStreamName = intent.getStringExtra("news_stream_name")
            val flag = intent.getStringExtra("flag")

            if(jsonArticles != null && newsStreamName != null) {
                val articlesListType = object : TypeToken<List<ArticleDto>>() { }.type
                val articles = Gson().fromJson<List<ArticleDto>>(jsonArticles, articlesListType)

                streamToAdapterMap[newsStreamName]!!.onGetArticles(articles)
            } else {
                Toast.makeText(context, context!!.getString(R.string.broadcastInvalidExtrasError),
                    Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, context!!.getString(R.string.broadcastExtrasError),
                Toast.LENGTH_LONG).show()
        }
    }

    /*This is potentially a memory leak, since when you go and delete a news stream, this broadcast
     receiver will still have a reference to the adapter of the fragment of the deleted stream. This
     receiver lives as long as the main activity lives, so this could be a memory leak if you
     deleted a lot of streams while being in the app.*/
    fun setAdapter(newsStreamName: String, adapter: NewsCardAdapter) {
        streamToAdapterMap[newsStreamName] = adapter
    }

    private fun acquirePermissions(context: Context) {
        val securityLevel = loadSecuritySettingsFromFile(context)

        if(securityLevel == context.getString(R.string.mediumSecurityLevel).toLowerCase()) {
            ContextCompat.checkSelfPermission(context,
                    "uk.ac.swansea.dascalu.dvmicc.newsaggregator.permissions.READ_NEWS_N")
        } else if(securityLevel == context.getString(R.string.highSecurityLevel).toLowerCase()) {

        } else if(securityLevel == context.getString(R.string.veryHighSecurityLevel).toLowerCase()) {

        }
    }
}