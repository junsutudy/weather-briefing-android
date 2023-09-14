package app.junsu.weather.data

@JvmInline
value class Humidity(
    val percentage: Float,
) {
    val level: Level
        get() = when (this.percentage) {
            in 0.0..0.2 -> Level.VERY_LOW
            in 0.2..0.4 -> Level.LOW
            in 0.4..0.6 -> Level.NORMAL
            in 0.6..0.8 -> Level.HIGH
            in 0.8..1.0 -> Level.VERY_HIGH
            else -> Level.NORMAL
        }

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