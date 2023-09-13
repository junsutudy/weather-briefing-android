package app.junsu.weather.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.junsu.weather.R
import app.junsu.weather.data.WeatherStatus
import app.junsu.weather.ui.theme.BackgroundAfternoon
import app.junsu.weather.ui.theme.BackgroundDawn
import app.junsu.weather.ui.theme.BackgroundMidday
import app.junsu.weather.ui.theme.BackgroundMidnight
import app.junsu.weather.ui.theme.BackgroundMorning
import app.junsu.weather.ui.theme.BackgroundNight
import app.junsu.weather.ui.theme.BackgroundSunrise
import app.junsu.weather.ui.theme.BackgroundSunset
import app.junsu.weather.util.TimePart
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
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(start = 8.dp),
                )
                HumidityCard(
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(end = 8.dp),
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
                        .weight(0.7f)
                        .padding(start = 8.dp),
                )
                MoreInformationCard(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(end = 8.dp),
                )
            }
            HeadlineCard(
                modifier = Modifier.weight(
                    weight = 1f,
                    fill = false,
                ),
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
    @Composable inline get() = when (this) {
        WeatherStatus.SUNNY -> stringResource(id = R.string.weather_sunny)
        WeatherStatus.CLOUDY -> stringResource(id = R.string.weather_cloudy)
        WeatherStatus.RAINY -> stringResource(id = R.string.weather_rainy)
        WeatherStatus.SNOWY -> stringResource(id = R.string.weather_snowy)
        null -> stringResource(id = R.string.weather_loading)
    }

@Composable
private fun FineDustCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { },
        colors = cardColors,
    ) {
        Box(modifier = Modifier.size(128.dp))
    }
}

@Composable
private fun HumidityCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { },
        colors = cardColors,
    ) {
        Box(modifier = Modifier.size(128.dp))
    }
}

@Composable
private fun UvCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { },
        colors = cardColors,
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.size(80.dp),
                model = R.drawable.img_uv,
                contentDescription = "uv image",
            )
        }
    }
}

@Composable
private fun MoreInformationCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { },
        colors = cardColors,
    ) {
        Box(modifier = Modifier.size(128.dp))
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
private fun HeadlineCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = cardColors,
    ) {
        Box(modifier = Modifier.size(128.dp))
    }
}
