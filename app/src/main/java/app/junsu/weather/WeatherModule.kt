package app.junsu.weather

import app.junsu.weather.data.datasource.network.WeatherNetworkDataSource
import app.junsu.weather.data.datasource.network.crawler.WeatherCrawler
import app.junsu.weather.data.repository.WeatherRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val weatherModule: Module
    get() = module {
        includes(dataModule)
        viewModelOf(::MainActivityViewModel)
        viewModel<MainActivityViewModel> { MainActivityViewModel(get()) }
    }

val dataModule: Module
    get() = module {
        single<WeatherCrawler> { WeatherCrawler(searchedWeatherUrl) }
        single<WeatherNetworkDataSource> { WeatherNetworkDataSource(get()) }
        single<WeatherRepository> { WeatherRepository(get()) }
    }
