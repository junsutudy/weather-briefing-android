package app.junsu.weather.data.datasource.network

import app.junsu.weather.data.FineDustStatus
import app.junsu.weather.data.Humidity
import app.junsu.weather.data.UvStatus
import app.junsu.weather.data.WeatherStatus
import app.junsu.weather.data.datasource.network.crawler.WeatherCrawler
import kotlinx.coroutines.flow.Flow

class WeatherNetworkDataSource(
    private val weatherCrawler: WeatherCrawler,
) {
    fun temperatureFlow(): Flow<Float> = weatherCrawler.temperature
    fun weatherStatusFlow(): Flow<WeatherStatus> = weatherCrawler.weatherStatus
    fun fineDustStatusFlow(): Flow<FineDustStatus> = weatherCrawler.fineDustStatus
    fun humidityFlow(): Flow<Humidity> = weatherCrawler.humidity
    fun uvStatusFlow(): Flow<UvStatus> = weatherCrawler.uvStatus
}
