package app.junsu.weather

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
        viewModel<MainActivityViewModel> { MainActivityViewModel(get()) }
        viewModel<WeatherViewModel> { WeatherViewModel(get()) }
    }

val dataModule: Module
    get() = module {
        single<NewsCrawler> { NewsCrawler(searchedNewsUrl) }
        single<WeatherCrawler> { WeatherCrawler(searchedWeatherUrl) }
        single<NewsNetworkDataSource> { NewsNetworkDataSource(get()) }
        single<WeatherNetworkDataSource> { WeatherNetworkDataSource(get()) }
        single<NewsRepository> { NewsRepository(get()) }
        single<WeatherRepository> { WeatherRepository(get()) }
    }
