package app.junsu.weather.util

import java.time.LocalDateTime

enum class TimePart {
    DAWN, SUNRISE, MORNING, MIDDAY, AFTERNOON, SUNSET, NIGHT, MIDNIGHT,
    ;

    companion object {
        fun from(currentTime: LocalDateTime) = when (currentTime.hour) {
            in 1 until 5 -> DAWN
            in 5 until 7 -> SUNRISE
            in 7 until 11 -> MORNING
            in 11 until 13 -> MIDDAY
            in 13 until 17 -> AFTERNOON
            in 17 until 19 -> SUNSET
            in 19 until 23 -> NIGHT
            23, 0 -> MIDNIGHT
            else -> throw IllegalStateException()
        }

        fun fromNow() = this.from(LocalDateTime.now())
    }
}
