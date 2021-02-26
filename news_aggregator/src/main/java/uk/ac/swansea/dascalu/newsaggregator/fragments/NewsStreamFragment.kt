package uk.ac.swansea.dascalu.newsaggregator.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.enums.Language
import com.dfl.newsapi.enums.SortBy
import com.dfl.newsapi.model.ArticleDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.schedulers.Schedulers

import uk.ac.swansea.dascalu.newsaggregator.Database
import uk.ac.swansea.dascalu.newsaggregator.adapters.NewsCardAdapter
import uk.ac.swansea.dascalu.newsaggregator.R

import java.lang.StringBuilder

class NewsStreamFragment(private val newsStreamName: String) : Fragment() {
    private val debugMode: Boolean = false
    private lateinit var newsApi: NewsApiRepository
    private lateinit var newsCardAdapter : NewsCardAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        newsApi = NewsApiRepository(context.getString(R.string.news_api_key))
        newsCardAdapter = NewsCardAdapter(listOf<ArticleDto>(), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newsStreamRootView = inflater.inflate(R.layout.news_stream_fragment, container, false)

        return newsStreamRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(debugMode) {
            getdefaultArticles()
        } else {
            subscribeForArticles()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.news_stream_recycler_view)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = newsCardAdapter

        val swipeContainer = view.findViewById<SwipeRefreshLayout>(R.id.swipeLayout)
        swipeContainer.setOnRefreshListener {
            if(debugMode) {
                swipeContainer.isRefreshing = false
                getdefaultArticles()
            } else {
                subscribeForArticles(swipeContainer)
            }
        }
    }

     private fun subscribeForArticles(swipeLayout: SwipeRefreshLayout? = null) {
        val articles = mutableListOf<ArticleDto>()

         if(newsStreamName != context!!.getString(R.string.recommended)) {
             newsApi.getEverything(q = getKeywordQuery(), domains = null, language = Language.EN, sortBy = SortBy.RELEVANCY, pageSize = 20, page = 1)
                 .subscribeOn(Schedulers.io())
                 .toFlowable()
                 .flatMapIterable { articlesDto -> articlesDto.articles }
                 .subscribe(
                     //onNext
                     {
                             article -> articles.add(article)
                     },
                     //onError
                     {
                             t -> Log.d("news api error", t.message!!)
                     },
                     //onComplete
                     {
                         articles.shuffle()
                         if(swipeLayout != null) {
                             activity!!.runOnUiThread {
                                 swipeLayout.isRefreshing = false
                             }
                         }

                         newsCardAdapter.onGetArticles(articles)
                     }
                 )
         } else {
             newsApi.getTopHeadlines(q = getKeywordQuery(), pageSize = 20, page = 1)
                 .subscribeOn(Schedulers.io())
                 .toFlowable()
                 .flatMapIterable { articlesDto -> articlesDto.articles }
                 .subscribe(
                     //onNext
                     { article -> articles.add(article) },
                     //onError
                     {
                             t -> Log.d("news api error", t.message!!)
                     },
                     //onComplete
                     {
                         articles.shuffle()
                         if(swipeLayout != null) {
                             activity!!.runOnUiThread {
                                 swipeLayout.isRefreshing = false
                             }
                         }
                         newsCardAdapter.onGetArticles(articles)
                     }
                 )
         }
    }

    private fun getKeywordQuery(): String {
        val streamKeywords = Database.instance.getKeywordsForStream(newsStreamName)

        if(streamKeywords.isEmpty()) {
            return ""
        }

        val builder = StringBuilder()
        builder.append("\"")
        builder.append(streamKeywords[0])
        builder.append("\"")

        for(i in 1 until streamKeywords.size) {
            builder.append(" OR ")
            builder.append("\"")
            builder.append(streamKeywords[i])
            builder.append("\"")
        }

        return builder.toString()
    }

    private fun getdefaultArticles() {
        val jsonArticles = "[ {\"author\":\"Kris Holt\",\"description\":\"Returnal, the upcoming PS5 exclusive from Resogun studio Housemarque, will arrive a little later than expected. The studio and Sony have pushed back the release date from March 19th to April 30th to give Housemarque more time to “polish the game to the level …\",\"publishedAt\":\"2021-01-28T18:57:08Z\",\"source\":{\"id\":\"engadget\",\"name\":\"Engadget\"},\"title\":\"Sony pushes PS5 exclusive Returnal\\u0027s release date to April 30th\",\"url\":\"https://www.engadget.com/ps5-exclusive-returnal-delayed-release-date-housemarque-sony-185708059.html\",\"urlToImage\":\"https://o.aolcdn.com/images/dims?resize\\u003d1200%2C630\\u0026crop\\u003d1200%2C630%2C0%2C0\\u0026quality\\u003d95\\u0026image_uri\\u003dhttps%3A%2F%2Fs.yimg.com%2Fos%2Fcreatr-uploaded-images%2F2021-01%2F98649030-6199-11eb-bd79-1b73b22d935b\\u0026client\\u003damp-blogside-v2\\u0026signature\\u003d4b7b8bd45d4241d8ca41c4b4ff1f4030aeb41e16\"}, {\"author\":\"Jay Peters\",\"description\":\"Cyberpunk 2077 developer CD Projekt Red has released hotfix 1.12 for the PC version of the game. The update fixes a vulnerability that could be used to “execute code on PCs” if you installed mods or custom save files.\",\"publishedAt\":\"2021-02-05T17:37:38Z\",\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"title\":\"New Cyberpunk 2077 hotfix lets you safely install mods\",\"url\":\"https://www.theverge.com/2021/2/5/22268382/cyberpunk-2077-1-12-new-hotfix-patch-mods-vulnerability-security\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/5oKhijS3ZoG3iuXQuVJFtY0jD1w\\u003d/0x38:1920x1043/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22284174/Cyberpunk2077_Always_bring_a_gun_to_a_knife_fight_RGB_en.jpg\"} ]"

        val articlesListType = object : TypeToken<List<ArticleDto>>() { }.type
        val artilesDtoList = Gson().fromJson<List<ArticleDto>>(jsonArticles, articlesListType)
        newsCardAdapter.onGetArticles(artilesDtoList)
    }
}