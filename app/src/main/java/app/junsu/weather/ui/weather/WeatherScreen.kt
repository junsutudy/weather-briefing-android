package app.junsu.weather.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.junsu.weather.R
import app.junsu.weather.data.FineDustStatus
import app.junsu.weather.data.HeadlineNews
import app.junsu.weather.data.Humidity
import app.junsu.weather.data.UvStatus
import app.junsu.weather.data.WeatherStatus
import app.junsu.weather.data.datasource.network.crawler.SEARCHED_FINE_DUST_URL
import app.junsu.weather.data.datasource.network.crawler.SEARCHED_HUMIDITY_URL
import app.junsu.weather.data.datasource.network.crawler.SEARCHED_UV_URL
import app.junsu.weather.data.datasource.network.crawler.SEARCHED_WEATHER_URL
import app.junsu.weather.ui.theme.BackgroundAfternoon
import app.junsu.weather.ui.theme.BackgroundDawn
import app.junsu.weather.ui.theme.BackgroundMidday
import app.junsu.weather.ui.theme.BackgroundMidnight
import app.junsu.weather.ui.theme.BackgroundMorning
import app.junsu.weather.ui.theme.BackgroundNight
import app.junsu.weather.ui.theme.BackgroundSunrise
import app.junsu.weather.ui.theme.BackgroundSunset
import app.junsu.weather.util.TimePart
import app.junsu.weather.util.bouncingClickable
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = koinViewModel(),
) {
    val uiState by weatherViewModel.stateFlow.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = currentGradientBackgroundBrush),
    ) {
        WeatherBanner(
            modifier = Modifier.fillMaxWidth(),
            temperature = uiState.weather?.temperature,
            weatherStatus = uiState.weather?.weatherStatus,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .weight(
                        weight = 1f,
                        fill = false,
                    )
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FineDustCard(
                    modifier = Modifier.padding(start = 8.dp),
                    fineDustStatus = uiState.weather?.fineDustStatus,
                    onClick = { uriHandler.openUri(SEARCHED_FINE_DUST_URL) },
                )
                HumidityCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    humidity = uiState.weather?.humidity,
                    onClick = { uriHandler.openUri(SEARCHED_HUMIDITY_URL) },
                )
            }
            Row(
                modifier = Modifier.weight(
                    weight = 1f,
                    fill = false,
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                UvCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    uvStatus = uiState.weather?.uvStatus,
                    onClick = { uriHandler.openUri(SEARCHED_UV_URL) },
                )
                MoreInformationCard(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = { uriHandler.openUri(SEARCHED_WEATHER_URL) },
                )
            }
            HeadlineNewsCard(
                modifier = Modifier.weight(
                    weight = 1f,
                    fill = false,
                ),
                headlineNews = uiState.headlineNews,
                onClick = {
                    if (uiState.headlineNews?.link != null) {
                        uriHandler.openUri(uiState.headlineNews!!.link)
                    }
                },
            )
        }
    }
}

// todo use Immutable Collection
@Stable
private val currentBackgroundColor: Color = when (TimePart.fromNow()) {
    TimePart.DAWN -> BackgroundDawn
    TimePart.SUNRISE -> BackgroundSunrise
    TimePart.MORNING -> BackgroundMorning
    TimePart.MIDDAY -> BackgroundMidday
    TimePart.AFTERNOON -> BackgroundAfternoon
    TimePart.SUNSET -> BackgroundSunset
    TimePart.NIGHT -> BackgroundNight
    TimePart.MIDNIGHT -> BackgroundMidnight
}

@Stable
private val currentBackgroundColors = listOf(
    currentBackgroundColor.copy(alpha = 0.5f),
    currentBackgroundColor.copy(alpha = 0.4f),
    currentBackgroundColor.copy(alpha = 0.2f),
)

@Stable
private val currentGradientBackgroundBrush = Brush.verticalGradient(currentBackgroundColors)

@Stable
private val cardBackgroundColor: Color
    @Composable inline get() = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)

@Stable
private val cardColors: CardColors
    @Composable inline get() = CardDefaults.cardColors(
        containerColor = cardBackgroundColor,
    )

@Composable
private fun WeatherBanner(
    modifier: Modifier = Modifier,
    temperature: Float?,
    weatherStatus: WeatherStatus?,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(getWeatherAnimationRaw(weatherStatus)),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        LottieAnimation(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                )
                .size(120.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.FillWidth,
        )
        Spacer(modifier = Modifier.weight(1f))
        TemperatureCard(
            temperature = temperature,
            weatherStatus = weatherStatus,
        )
    }
}

@Composable
private fun TemperatureCard(
    modifier: Modifier = Modifier,
    temperature: Float?,
    weatherStatus: WeatherStatus?,
) {
    Card(
        modifier = modifier.padding(end = 8.dp),
        colors = cardColors,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.End,
            ),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 16.dp,
                    bottom = 8.dp,
                ),
                text = weatherStatus.text + ',',
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = MaterialTheme.typography.displayLarge.toSpanStyle(),
                    ) {
                        if (temperature != null) {
                            append(temperature.toString())
                        }
                    }
                    withStyle(
                        style = MaterialTheme.typography.bodyMedium.toSpanStyle(),
                    ) {
                        append("℃")
                    }
                },
            )
        }
    }
}

private val WeatherStatus?.text: String
    @Composable inline get() = stringResource(
        id = when (this) {
            WeatherStatus.SUNNY -> R.string.weather_sunny
            WeatherStatus.CLOUDY -> R.string.weather_cloudy
            WeatherStatus.RAINY -> R.string.weather_rainy
            WeatherStatus.SNOWY -> R.string.weather_snowy
            null -> R.string.weather_loading
        },
    )

@Composable
private fun FineDustCard(
    modifier: Modifier = Modifier,
    fineDustStatus: FineDustStatus?,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.bouncingClickable(onClick = onClick),
        colors = cardColors,
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (fineDustStatus != null) {
                Image(
                    modifier = Modifier.weight(1f),
                    painter = fineDustStatus.painterRes,
                    contentDescription = "more",
                )
            } else {
                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(resId = R.raw.animation_loading),
                )

                LottieAnimation(
                    modifier = Modifier.weight(1f),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.FillWidth,
                )
            }
            Text(
                text = fineDustStatus.text,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

private val FineDustStatus.painterRes: Painter
    @Composable inline get() = painterResource(
        id = when (this) {
            FineDustStatus.VERY_BAD -> R.drawable.img_fine_dust_very_bad
            FineDustStatus.BAD -> R.drawable.img_fine_dust_bad
            FineDustStatus.NORMAL -> R.drawable.img_fine_dust_normal
            FineDustStatus.GOOD -> R.drawable.img_fine_dust_good
        },
    )

private val FineDustStatus?.text: String
    @Composable inline get() = stringResource(
        id = when (this) {
            FineDustStatus.VERY_BAD -> R.string.fine_dust_very_bad
            FineDustStatus.BAD -> R.string.fine_dust_bad
            FineDustStatus.NORMAL -> R.string.fine_dust_normal
            FineDustStatus.GOOD -> R.string.fine_dust_good
            null -> R.string.fine_dust_loading
        },
    )

@Composable
private fun HumidityCard(
    modifier: Modifier = Modifier,
    humidity: Humidity?,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .bouncingClickable(onClick = onClick),
        colors = cardColors,
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = currentBackgroundColor,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .width(4.dp)
                    .fillMaxHeight(),
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 8.dp,
                        )
                        .fillMaxWidth(),
                    text = String.format(
                        stringResource(id = R.string.humidity),
                        humidity?.level.text,
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    modifier = Modifier.padding(
                        start = 8.dp,
                        bottom = 8.dp,
                    ),
                    text = "늦은 저녁 비가 예상되어요.",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

private val Humidity.Level?.text: String
    @Composable inline get() = stringResource(
        id = when (this) {
            Humidity.Level.VERY_HIGH -> R.string.humidity_very_high
            Humidity.Level.HIGH -> R.string.humidity_high
            Humidity.Level.NORMAL -> R.string.humidity_normal
            Humidity.Level.LOW -> R.string.humidity_low
            Humidity.Level.VERY_LOW -> R.string.humidity_very_low
            null -> R.string.humidity_loading
        },
    )

@Composable
private fun UvCard(
    modifier: Modifier = Modifier,
    uvStatus: UvStatus?,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .bouncingClickable(onClick = onClick),
        colors = cardColors,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                    )
                    .size(80.dp),
                model = R.drawable.img_uv,
                contentDescription = "uv image",
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 8.dp,
                        )
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.uv),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    modifier = Modifier.padding(
                        start = 8.dp,
                        bottom = 8.dp,
                    ),
                    text = uvStatus.text,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

private val UvStatus?.text: String
    @Composable inline get() = stringResource(
        id = when (this) {
            UvStatus.VERY_HIGH -> R.string.uv_very_high
            UvStatus.HIGH -> R.string.uv_high
            UvStatus.NORMAL -> R.string.uv_normal
            UvStatus.GOOD -> R.string.uv_good
            null -> R.string.uv_loading
        },
    )

@Composable
private fun MoreInformationCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.bouncingClickable(onClick = onClick),
        colors = cardColors,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp),
                painter = painterResource(id = R.drawable.img_more),
                contentDescription = "more",
            )
        }
    }
}

private fun getWeatherAnimationRaw(weatherStatus: WeatherStatus?): Int = when (weatherStatus) {
    WeatherStatus.SUNNY -> R.raw.animation_weather_sunny
    WeatherStatus.CLOUDY -> R.raw.animation_weather_cloudy
    WeatherStatus.RAINY -> R.raw.animation_weather_rainy
    WeatherStatus.SNOWY -> R.raw.animation_weather_snowy
    null -> R.raw.animation_loading
}

@Composable
private fun HeadlineNewsCard(
    modifier: Modifier = Modifier,
    headlineNews: HeadlineNews?,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .bouncingClickable(onClick = onClick),
        colors = cardColors,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (headlineNews != null) {
                Text(
                    modifier = Modifier.padding(
                        start = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                        // todo remove
                        end = 8.dp,
                    ),
                    text = headlineNews.title,
                    style = MaterialTheme.typography.titleMedium,
                )
            } else {
                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(resId = R.raw.animation_loading),
                )

                LottieAnimation(
                    modifier = Modifier.size(80.dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }
}
