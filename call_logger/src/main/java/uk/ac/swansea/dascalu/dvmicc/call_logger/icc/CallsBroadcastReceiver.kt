package uk.ac.swansea.dascalu.dvmicc.call_logger.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dfl.newsapi.model.ArticleDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CallsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.extras != null) {
            val jsonArticles = intent.getStringExtra("articles")
            val newsStreamName = intent.getStringExtra("news_stream_name")
            val flag = intent.getStringExtra("flag")

            if (jsonArticles != null) {
                val articlesListType = object : TypeToken<List<ArticleDto>>() {}.type
                val articles = Gson().fromJson<List<ArticleDto>>(jsonArticles, articlesListType)

            }
        }
    }
}