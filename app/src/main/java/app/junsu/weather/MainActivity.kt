package app.junsu.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import app.junsu.weather.data.WeatherStatus
import app.junsu.weather.ui.theme.BackgroundAfternoon
import app.junsu.weather.ui.theme.BackgroundDawn
import app.junsu.weather.ui.theme.BackgroundMidday
import app.junsu.weather.ui.theme.BackgroundMidnight
import app.junsu.weather.ui.theme.BackgroundMorning
import app.junsu.weather.ui.theme.BackgroundNight
import app.junsu.weather.ui.theme.BackgroundSunrise
import app.junsu.weather.ui.theme.BackgroundSunset
import app.junsu.weather.ui.theme.WeatherBriefingTheme
import app.junsu.weather.ui.weather.WeatherViewModel
import app.junsu.weather.util.TimePart
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
        setContent {
            WeatherBriefingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    WeatherApp(
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
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

@Composable
private fun WeatherApp(
    modifier: Modifier = Modifier,
    //todo
    weatherViewModel: WeatherViewModel = koinViewModel(),
) {
    val uiState by weatherViewModel.flow.collectAsState()
    Scaffold { padValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(brush = currentGradientBackgroundBrush)
                .padding(padValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            WeatherBanner(
                modifier = Modifier.fillMaxWidth(),
                // weatherStatus = uiState.value.
                temperature = uiState.temperature,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Card(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(start = 8.dp),
                    colors = cardColors,
                ) {
                    Box(modifier = Modifier.size(128.dp))
                }
                FineDustCard(
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(end = 8.dp),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Card(
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(start = 8.dp),
                    colors = cardColors,
                ) {
                    Box(modifier = Modifier.size(128.dp))
                }
                Card(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(end = 8.dp),
                    colors = cardColors,
                ) {
                    Box(modifier = Modifier.size(128.dp))
                }
            }
        }
    }
}

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
    // weatherStatus: WeatherStatus,
    temperature: Float,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(getWeatherAnimationRaw(WeatherStatus.CLOUDY)),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(120.dp)
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                ),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier.weight(0.6f),
            horizontalAlignment = Alignment.End,
        ) {
            Card(
                modifier = Modifier.padding(end = 8.dp),
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
                        text = "맑음" + ',',
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        modifier = Modifier.padding(end = 16.dp),
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.displayLarge.toSpanStyle(),
                            ) {
                                append(temperature.toString())
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
    }
}

private fun getWeatherAnimationRaw(weatherStatus: WeatherStatus): Int = when (weatherStatus) {
    WeatherStatus.SUNNY -> R.raw.animation_weather_sunny
    WeatherStatus.CLOUDY -> R.raw.animation_weather_cloudy
    WeatherStatus.RAINY -> R.raw.animation_weather_rainy
    WeatherStatus.SNOWY -> R.raw.animation_weather_snowy
}

@Composable
private fun FineDustCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = cardColors,
    ) {
        Box(modifier = Modifier.size(128.dp))
    }
}
