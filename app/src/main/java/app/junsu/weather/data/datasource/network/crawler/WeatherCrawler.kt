package app.junsu.weather.data.datasource.network.crawler

import app.junsu.weather.data.FineDustStatus
import app.junsu.weather.data.Humidity
import app.junsu.weather.data.UvStatus
import app.junsu.weather.data.WeatherStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class WeatherCrawler(
    override val url: String,
) : Crawler {
    private val connection: Connection = Jsoup.connect(url)
    private val jsoupDoc: Document = runBlocking(Dispatchers.IO) {
        connection.get()
    }

    val temperature: Flow<Float>
        get() = flow {
            val stringValue =
                jsoupDoc.select("div.temperature_text")[0].text().substring(5).dropLast(1)
            emit(stringValue.toFloat())
        }

    val weatherStatus: Flow<WeatherStatus>
        get() = flow {
            val koreanValue = jsoupDoc.getElementsByClass("weather before_slash")[0].text()
            emit(WeatherStatus.fromString(koreanValue))
        }

    val fineDustStatus: Flow<FineDustStatus>
        get() = flow {
            val koreanValue =
                jsoupDoc.getElementsByClass("item_today")[0].text().substring(5)
            emit(FineDustStatus.fromString(koreanValue))
        }

    val humidity: Flow<Humidity>
        get() = flow {
            val koreanValue = jsoupDoc.select(".summary_list")[0].text().substring(12, 15)
            emit(Humidity.of(koreanValue))
        }

    val uvStatus: Flow<UvStatus>
        get() = flow {
            val koreanValue = jsoupDoc.getElementsByClass("item_today")[2].text()
            emit(UvStatus.fromString(koreanValue))
        }
}
