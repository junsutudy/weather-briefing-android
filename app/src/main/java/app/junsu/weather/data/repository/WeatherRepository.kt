package app.junsu.weather.data.repository

import app.junsu.weather.data.Weather
import app.junsu.weather.data.datasource.network.WeatherNetworkDataSource
import kotlinx.coroutines.flow.first

class WeatherRepository(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
) {
    suspend fun fetchWeather(): Weather {
        return Weather(
            temperature = weatherNetworkDataSource.temperatureFlow().first(),
            weatherStatus = weatherNetworkDataSource.weatherStatusFlow().first(),
            fineDustStatus = weatherNetworkDataSource.fineDustStatusFlow().first(),
            humidity = weatherNetworkDataSource.humidityFlow().first(),
            uvStatus = weatherNetworkDataSource.uvStatusFlow().first(),
        )
    }
}
