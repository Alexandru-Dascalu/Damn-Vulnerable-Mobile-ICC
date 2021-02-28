package uk.ac.swansea.dascalu.dvmicc.newsaggregator

import com.dfl.newsapi.model.ArticleDto

interface NewsApiCallback {
    fun onGetArticles(articles: List<ArticleDto>)
}