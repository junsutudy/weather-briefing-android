package app.junsu.weather.util

import java.time.LocalDateTime

enum class TimePart {
    SUNRISE, MORNING, MIDDAY, AFTERNOON, SUNSET, MIDNIGHT,
    ;

    companion object {

        fun from(currentTime: LocalDateTime = LocalDateTime.now()) = when (currentTime.hour) {
            in 2 until 6 -> SUNRISE
            in 6 until 10 -> MORNING
            in 10 until 14 -> MIDDAY
            in 14 until 18 -> AFTERNOON
            in 18 until 22 -> SUNSET
            22, 23, 0, 1 -> MIDNIGHT
            else -> throw IllegalStateException()
        }
    }
}
