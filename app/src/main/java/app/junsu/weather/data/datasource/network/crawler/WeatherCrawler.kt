package app.junsu.weather.data.datasource.network.crawler

import app.junsu.weather.data.FineDustStatus
import app.junsu.weather.data.UvStatus
import app.junsu.weather.data.WeatherStatus
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class WeatherCrawler(
    override val url: String,
) : Crawler {
    private val connection: Connection
        get() = Jsoup.connect(url)
    private val jsoupDoc: Document
        get() = connection.get()

    val temperature: String
        get() {
            return jsoupDoc.select("div.temperature_text")[0].text().substring(5)
        }

    val weatherStatus: WeatherStatus
        get() {
            val koreanValue = jsoupDoc.getElementsByClass("weather before_slash")[0].text()
            return WeatherStatus.fromString(koreanValue)
        }

    val fineDustStatus: FineDustStatus
        get() {
            val koreanValue =
                jsoupDoc.getElementsByClass("item_today level2")[0].text().substring(5)
            return FineDustStatus.fromString(koreanValue)
        }

    val humidityStatus: String
        get() {
            return jsoupDoc.select(".summary_list")[0].text().substring(12, 15)
        }

    val uvStatus: UvStatus
        get() {
            val koreanValue = jsoupDoc.getElementsByClass("item_today level1")[0].text()
            return UvStatus.fromString(koreanValue)
        }
}
