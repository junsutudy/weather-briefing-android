package app.junsu.weather

import app.junsu.weather.data.datasource.network.crawler.SEARCHED_NEWS_URL
import app.junsu.weather.data.datasource.network.crawler.SEARCHED_WEATHER_URL
import app.junsu.weather.data.datasource.network.NewsNetworkDataSource
import app.junsu.weather.data.datasource.network.WeatherNetworkDataSource
import app.junsu.weather.data.datasource.network.crawler.NewsCrawler
import app.junsu.weather.data.datasource.network.crawler.WeatherCrawler
import app.junsu.weather.data.repository.NewsRepository
import app.junsu.weather.data.repository.WeatherRepository
import app.junsu.weather.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val weatherModule: Module
    get() = module {
        includes(dataModule)
        viewModel<MainActivityViewModel> { MainActivityViewModel() }
        viewModel<WeatherViewModel> { WeatherViewModel(get(), get()) }
    }

val dataModule: Module
    get() = module {
        single<NewsCrawler> { NewsCrawler(SEARCHED_NEWS_URL) }
        single<WeatherCrawler> { WeatherCrawler(SEARCHED_WEATHER_URL) }
        single<NewsNetworkDataSource> { NewsNetworkDataSource(get()) }
        single<WeatherNetworkDataSource> { WeatherNetworkDataSource(get()) }
        single<NewsRepository> { NewsRepository(get()) }
        single<WeatherRepository> { WeatherRepository(get()) }
    }
