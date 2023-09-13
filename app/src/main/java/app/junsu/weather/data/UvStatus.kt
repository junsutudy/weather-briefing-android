package app.junsu.weather.data

enum class UvStatus {
    VERY_HIGH,
    HIGH,
    NORMAL,
    GOOD,
    ;

    companion object {
        fun fromString(value: String): UvStatus = when (value) {
            "매우", "매우높음" -> VERY_HIGH
            "높음" -> HIGH
            "보통" -> NORMAL
            "좋음" -> GOOD
            else -> NORMAL
        }
    }
}
