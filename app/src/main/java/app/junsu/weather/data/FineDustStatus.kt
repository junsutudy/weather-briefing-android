package app.junsu.weather.data

enum class FineDustStatus {
    VERY_BAD,
    BAD,
    NORMAL,
    GOOD,
    ;

    companion object {
        fun fromString(value: String): FineDustStatus = when (value) {
            "매우", "매우나쁨" -> VERY_BAD
            "나쁨" -> BAD
            "보통" -> NORMAL
            "좋음" -> GOOD
            else -> NORMAL
        }
    }
}
