package app.junsu.weather.data.datasource.network

import app.junsu.weather.data.Weather
import app.junsu.weather.data.datasource.network.crawler.WeatherCrawler
import kotlinx.coroutines.flow.Flow

class WeatherNetworkDataSource(
    private val weatherCrawler: WeatherCrawler,
) {/*
    fun fetchWeather(): Weather = Weather(
        temperature = weatherCrawler.temperature,
        weatherStatus = weatherCrawler.weatherStatus,
        fineDustStatus = weatherCrawler.fineDustStatus,
        humidity = weatherCrawler.humidity,
        uvStatus = weatherCrawler.uvStatus,
    )*/
    fun fetchTemperature(): Flow<Float> = weatherCrawler.temperature
}
