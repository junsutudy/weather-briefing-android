package app.junsu.weather.data.repository

import app.junsu.weather.data.Weather
import app.junsu.weather.data.datasource.network.WeatherNetworkDataSource
import kotlinx.coroutines.flow.first

class WeatherRepository(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
) {
    suspend fun fetchWeather(): Weather {
        return Weather(
            temperature = weatherNetworkDataSource.fetchTemperature().first(),
            weatherStatus = weatherNetworkDataSource.fetchWeatherStatus().first(),
            fineDustStatus = weatherNetworkDataSource.fetchFineDustStatus().first(),
            humidity = weatherNetworkDataSource.fetchHumidity().first(),
            uvStatus = weatherNetworkDataSource.fetchUvStatus().first(),
        )
    }
}
