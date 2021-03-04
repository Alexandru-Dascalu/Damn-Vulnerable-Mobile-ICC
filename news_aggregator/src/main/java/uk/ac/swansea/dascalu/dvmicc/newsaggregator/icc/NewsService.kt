package uk.ac.swansea.dascalu.dvmicc.newsaggregator.icc

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.widget.Toast

import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.enums.Language
import com.dfl.newsapi.enums.SortBy
import com.dfl.newsapi.model.ArticleDto
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers

import uk.ac.swansea.dascalu.dvmicc.newsaggregator.R
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.model.Database
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.utils.loadSecuritySettingsFromFile

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

import java.lang.IllegalStateException
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class NewsService : Service() {
    companion object {
        const val NEWS_RESULT_BROADCAST_ACTION : String = "uk.ac.swansea.dascalu.dvmicc.broadcast.NEWS_RESULT"
    }

    private val debugMode: Boolean = true
    private lateinit var newsApi: NewsApiRepository
    private lateinit var newsStreamName: String
    private var securityLevel: String = ""

    override fun onCreate() {
        newsApi = NewsApiRepository(getString(R.string.news_api_key))
        securityLevel = loadSecuritySettingsFromFile(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent!!.extras != null) {
            newsStreamName = intent.extras!!.getString("news_stream")!!

            if(debugMode) {
                val thread = Thread { getdefaultArticles() }
                thread.start()
            } else {
                val articles = ArrayList<ArticleDto>()
                if(newsStreamName != getString(R.string.recommended)) {
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
                                sendResultBroadcast(makeResultBroadcast(articles))
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
                                sendResultBroadcast(makeResultBroadcast(articles))
                            }
                        )
                }
            }
        } else {
            throw IllegalStateException("Intent to start service has no extras!")
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun makeResultBroadcast(articles: ArrayList<ArticleDto>) : Intent {
        val jsonArticles = Gson().toJson(articles)
        return makeResultBroadcast(jsonArticles)
    }

    private fun makeResultBroadcast(articles: String) : Intent {
        val broadcast = Intent(NEWS_RESULT_BROADCAST_ACTION)
        broadcast.putExtra("articles", articles)
        broadcast.putExtra("news_stream_name", newsStreamName)

        var flag : String = ""
        if(securityLevel == getString(R.string.lowSecurityLevel).toLowerCase()) {
            flag = resources.getStringArray(R.array.flags)[0]
        } else if(securityLevel == getString(R.string.mediumSecurityLevel).toLowerCase()) {
            flag = resources.getStringArray(R.array.flags)[1]
        } else if(securityLevel == getString(R.string.highSecurityLevel).toLowerCase()) {
            flag = resources.getStringArray(R.array.flags)[2]
        } else if(securityLevel == getString(R.string.veryHighSecurityLevel).toLowerCase()) {
            flag = resources.getStringArray(R.array.flags)[3]
        } else if(securityLevel == getString(R.string.impossibleSecurityLevel).toLowerCase()) {
            flag = resources.getStringArray(R.array.flags)[4]
        } else {
            throw IllegalStateException("Security level has an invalid value!")
        }

        broadcast.putExtra("flag", flag)
        return broadcast
    }

    private fun sendResultBroadcast(broadcast: Intent) {
        if(securityLevel == getString(R.string.lowSecurityLevel).toLowerCase()) {
            sendBroadcast(broadcast)
        } else if(securityLevel == getString(R.string.mediumSecurityLevel).toLowerCase()) {
            sendBroadcast(broadcast,
                    "uk.ac.swansea.dascalu.dvmicc.newsaggregator.permissions.READ_NEWS_A")
        } else if(securityLevel == getString(R.string.highSecurityLevel).toLowerCase()) {
            sendBroadcast(broadcast,
                    "uk.ac.swansea.dascalu.dvmicc.newsaggregator.permissions.READ_NEWS_B")
        } else if(securityLevel == getString(R.string.veryHighSecurityLevel).toLowerCase()) {
            sendBroadcast(broadcast,
                    "uk.ac.swansea.dascalu.dvmicc.newsaggregator.permissions.READ_NEWS_C")

        } else if(securityLevel == getString(R.string.impossibleSecurityLevel).toLowerCase()) {

        } else {
            throw IllegalStateException("Security level has an invalid value!")
        }

        stopSelf()
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

        Thread.sleep(2000)
        sendResultBroadcast(makeResultBroadcast(jsonArticles))
    }
}