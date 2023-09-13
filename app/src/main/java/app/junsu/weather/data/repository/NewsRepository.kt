package app.junsu.weather.data.repository

import app.junsu.weather.data.HeadlineNews
import app.junsu.weather.data.datasource.network.NewsNetworkDataSource
import kotlinx.coroutines.flow.Flow

class NewsRepository(
    private val newsNetworkDataSource: NewsNetworkDataSource,
) {
    fun headlineNewsFlow(): Flow<HeadlineNews> {
        return newsNetworkDataSource.headlineNewsFlow()
    }
}
