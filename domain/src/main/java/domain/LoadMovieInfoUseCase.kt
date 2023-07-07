package domain

import common.model.Movie
import domain.repository.MovieRepository

interface LoadMovieInfoUseCase {
    suspend fun execute(imdbId: String): Movie
}

internal class LoadMovieInfoUseCaseImpl(
    private val movieRepository: MovieRepository
) : LoadMovieInfoUseCase {
    override suspend fun execute(imdbId: String): Movie {
        return movieRepository.searchMovieByImdbId(imdbId)
    }
}
