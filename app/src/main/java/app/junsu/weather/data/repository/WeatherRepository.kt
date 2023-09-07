package app.junsu.weather.data.repository

import app.junsu.weather.data.Weather
import app.junsu.weather.data.datasource.network.WeatherNetworkDataSource

class WeatherRepository(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
) {
    fun fetchWeather(): Weather =
        weatherNetworkDataSource.fetchWeather()
}
