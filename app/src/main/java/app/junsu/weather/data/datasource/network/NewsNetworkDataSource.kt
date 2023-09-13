package app.junsu.weather.data.datasource.network

import app.junsu.weather.data.HeadlineNews
import app.junsu.weather.data.datasource.network.crawler.NewsCrawler
import kotlinx.coroutines.flow.Flow

class NewsNetworkDataSource(
    private val newsCrawler: NewsCrawler,
) {
    fun headlineNewsFlow(): Flow<HeadlineNews> = newsCrawler.headlineNews
}
