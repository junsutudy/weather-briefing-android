package app.junsu.weather.data

enum class WeatherStatus {
    SUNNY, CLOUDY, RAINY, SNOWY,
    ;

    companion object {
        fun fromString(value: String): WeatherStatus = when (value) {
            "맑음" -> SUNNY
            "흐림", "구름조금", "구름많음", "번개", "뇌우" -> CLOUDY
            "비", "약한비", "강한비", "우박", "가끔 비", "비 또는 눈" -> RAINY
            "눈", "약한눈", "강한눈", "진눈깨비", "소낙눈" -> SNOWY
            else -> CLOUDY
        }
    }
}
