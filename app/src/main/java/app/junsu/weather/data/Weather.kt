package app.junsu.weather.data

data class Weather(
    val temperature: Float,
    val weatherStatus: WeatherStatus,
    val fineDustStatus: FineDustStatus,
    val humidity: Humidity,
    val uvStatus: UvStatus,
)
