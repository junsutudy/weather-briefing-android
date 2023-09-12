package app.junsu.weather.data

enum class UvStatus {
    VERY_HIGH,
    HIGH,
    NORMAL,
    GOOD,
    ;

    companion object {
        fun fromString(value: String): UvStatus = when (value) {
            "자외선 매우", "자외선 매우높음" -> VERY_HIGH
            "자외선 높음" -> HIGH
            "자외선 보통" -> NORMAL
            "자외선 좋음" -> GOOD
            else -> NORMAL
        }
    }
}
