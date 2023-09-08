package app.junsu.weather.data.repository

import app.junsu.weather.data.datasource.network.WeatherNetworkDataSource
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
) {
    /*
        fun fetchWeather(): Weather =
            weatherNetworkDataSource.fetchWeather()*/
    fun fetchTemperature(): Flow<Float> = weatherNetworkDataSource.fetchTemperature()
}
