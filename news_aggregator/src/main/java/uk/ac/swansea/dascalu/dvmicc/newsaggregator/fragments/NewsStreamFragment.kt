package uk.ac.swansea.dascalu.dvmicc.newsaggregator.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.dfl.newsapi.model.ArticleDto

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.MainActivity
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.adapters.NewsCardAdapter
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.R
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.icc.NewsService

class NewsStreamFragment(private val newsStreamName: String) : Fragment() {
    private lateinit var newsCardAdapter : NewsCardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newsStreamRootView = inflater.inflate(R.layout.news_stream_fragment, container, false)

        return newsStreamRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.news_stream_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        newsCardAdapter = NewsCardAdapter(listOf<ArticleDto>(), this)
        recyclerView.adapter = newsCardAdapter

        //make sure broadcast receiver knows about adapter so it can update adapter with new articles
        val mainActivity = context as MainActivity
        mainActivity.newsBroadcastReceiver!!.setAdapter(newsStreamName, newsCardAdapter)

        subscribeForArticles()

        val swipeContainer = view.findViewById<SwipeRefreshLayout>(R.id.swipeLayout)
        swipeContainer.setOnRefreshListener {
            subscribeForArticles(swipeContainer)

            if(swipeContainer != null) {
                swipeContainer.isRefreshing = false
            }
        }
    }

     @SuppressLint("UseRequireInsteadOfGet")
     private fun subscribeForArticles(swipeLayout: SwipeRefreshLayout? = null) {
         val serviceIntent = Intent(context!!, NewsService::class.java)
         serviceIntent.putExtra("news_stream", newsStreamName)
         context!!.startService(serviceIntent)
    }
}