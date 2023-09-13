package app.junsu.weather.data.datasource.network.crawler

import app.junsu.weather.data.HeadlineNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class NewsCrawler(
    override val url: String,
) : Crawler {
    private val connection: Connection = Jsoup.connect(url)
    private val jsoupDoc: Document = connection.get()

    val headline: Flow<HeadlineNews>
        get() = flow {
            val thumbnailImageUrl = jsoupDoc.getElementsByClass("thumb").select("img")[0].text()
            val title = jsoupDoc.getElementsByClass("info_title")[0].select("text").text()
            emit(HeadlineNews(thumbnailImageUrl, title))
        }
}
