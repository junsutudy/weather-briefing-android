package app.junsu.weather.data.repository

import app.junsu.weather.data.datasource.network.NewsNetworkDataSource

class NewsRepository(
    private val newsNetworkDataSource: NewsNetworkDataSource,
) {
}
