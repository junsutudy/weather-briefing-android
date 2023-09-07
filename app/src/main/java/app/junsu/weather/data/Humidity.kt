package app.junsu.weather.data

@JvmInline
value class Humidity(
    val percentage: Float,
) {/*
    val status: Level
        get() = when (this.percentage) {
            in 0.0 until 0.2 -> Level.VERY_LOW
            in (0.2..0.4)
        }*/

    enum class Level {
        VERY_HIGH,
        HIGH,
        NORMAL,
        LOW,
        VERY_LOW,
    }

    companion object {
        /**
         * only `n%` format allowed.
         */
        fun of(value: String): Humidity {
            val formatted = value.dropLast(1).toFloat()
            return Humidity(formatted)
        }
    }
}