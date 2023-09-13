package app.junsu.weather.data.datasource.network.crawler

import app.junsu.weather.data.HeadlineNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class NewsCrawler(
    override val url: String,
) : Crawler {
    private val connection: Connection = Jsoup.connect(url)
    private val jsoupDoc: Document = runBlocking(Dispatchers.IO) {
        connection.get()
    }

    val headlineNews: Flow<HeadlineNews>
        get() = flow {
            val news = jsoupDoc.getElementsByClass("news_wrap api_ani_send")[0]
            val thumbInformation =
                news.getElementsByClass("dsc_thumb")[0]
            val thumbLink = thumbInformation.attr("href")
            val thumbnailImageUrl = thumbInformation.getElementsByTag("img")[0].attr("src")
            val title = news.getElementsByClass("news_tit")[0].text()
            emit(
                value = HeadlineNews(
                    link = thumbLink,
                    thumbnailUrl = thumbnailImageUrl,
                    title = title,
                ),
            )
        }
}
