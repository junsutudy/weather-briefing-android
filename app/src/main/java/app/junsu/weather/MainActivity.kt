package app.junsu.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.junsu.weather.ui.theme.BackgroundAfternoon
import app.junsu.weather.ui.theme.BackgroundMidnight
import app.junsu.weather.ui.theme.BackgroundMorning
import app.junsu.weather.ui.theme.BackgroundSunrise
import app.junsu.weather.ui.theme.BackgroundSunset
import app.junsu.weather.ui.theme.WeatherBriefingTheme
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

@Composable
private fun WeatherApp(
    modifier: Modifier = Modifier,
) {
    val colors = listOf(
        BackgroundAfternoon.copy(alpha = 0.4f),
        BackgroundAfternoon.copy(alpha = 0.3f),
        Color.Transparent,
    )
    val brush = Brush.verticalGradient(colors)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = brush),
    ) {
        Spacer(modifier = Modifier.weight(0.1f))
        WeatherCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
        )
        Card(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray.copy(alpha = 0.2f),
            ),
        ) {}
    }
}

@Composable
private fun WeatherCard(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_weather_sunny),
    )

    Row(
        modifier = modifier.fillMaxSize(),
    ) {
        LottieAnimation(
            modifier = Modifier.weight(0.4f),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.weight(0.6f),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "9월 6일",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "23\'",
                style = MaterialTheme.typography.displayLarge,
            )
        }
    }
}
