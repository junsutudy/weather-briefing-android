package app.junsu.weather

import app.junsu.weather.data.datasource.network.WeatherNetworkDataSource
import app.junsu.weather.data.datasource.network.crawler.WeatherCrawler
import app.junsu.weather.data.repository.WeatherRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val weatherModule: Module
    inline get() = module {
        viewModel<MainActivityViewModel> { MainActivityViewModel(get()) }
        viewModelOf(::MainActivityViewModel)
    }

val dataModule: Module
    inline get() = module {
        single<WeatherCrawler> { WeatherCrawler(get()) }
        single<WeatherNetworkDataSource> { WeatherNetworkDataSource(get()) }
        single<WeatherRepository> { WeatherRepository(get()) }
    }
