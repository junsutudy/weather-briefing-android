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
    fun fetchTemperature(): Flow<Float> = weatherCrawler.temperature
    fun fetchWeatherStatus(): Flow<WeatherStatus> = weatherCrawler.weatherStatus
    fun fetchFineDustStatus(): Flow<FineDustStatus> = weatherCrawler.fineDustStatus
    fun fetchHumidity(): Flow<Humidity> = weatherCrawler.humidity
    fun fetchUvStatus(): Flow<UvStatus> = weatherCrawler.uvStatus
}
