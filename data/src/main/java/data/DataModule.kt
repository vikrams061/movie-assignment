package data

import domain.repository.MovieRepository
import org.koin.dsl.module

/**
Koin module for providing dependencies related to data layer.
This module provides the necessary dependencies for the data layer, including the MovieRepository.
*/

val dataModule = module {
/*
Provides a factory instance of MovieRepository by initializing MovieRepositoryImpl
with the required dependencies obtained from Koin.
*/
    factory<MovieRepository> {
        MovieRepositoryImpl(get(), get())
    }
}
