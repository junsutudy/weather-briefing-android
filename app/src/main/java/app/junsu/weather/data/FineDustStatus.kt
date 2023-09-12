package app.junsu.weather.data

enum class FineDustStatus {
    VERY_BAD,
    BAD,
    NORMAL,
    GOOD,
    ;

    companion object {
        fun fromString(value: String): FineDustStatus = when (value) {
            "미세먼지 매우", "미세먼지 매우나쁨" -> VERY_BAD
            "미세먼지 나쁨" -> BAD
            "미세먼지 보통" -> NORMAL
            "미세먼지 좋음" -> GOOD
            else -> NORMAL
        }
    }
}
