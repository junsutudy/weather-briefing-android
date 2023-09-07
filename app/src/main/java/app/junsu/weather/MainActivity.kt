package app.junsu.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.junsu.weather.ui.theme.BackgroundAfternoon
import app.junsu.weather.ui.theme.BackgroundDawn
import app.junsu.weather.ui.theme.BackgroundMidday
import app.junsu.weather.ui.theme.BackgroundMidnight
import app.junsu.weather.ui.theme.BackgroundMorning
import app.junsu.weather.ui.theme.BackgroundNight
import app.junsu.weather.ui.theme.BackgroundSunrise
import app.junsu.weather.ui.theme.BackgroundSunset
import app.junsu.weather.ui.theme.WeatherBriefingTheme
import app.junsu.weather.util.TimePart
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
) {
    Scaffold { padValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(brush = currentGradientBackgroundBrush)
                .padding(padValues),
        ) {
            WeatherBanner(
                modifier = Modifier.fillMaxWidth(),
            )
            FineDustCard(
                modifier = Modifier.fillMaxWidth(),
            )
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
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_weather_rainy),
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
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "9월 6일",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "23\'",
                        style = MaterialTheme.typography.displayLarge,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FineDustCard(
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
