package domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import common.model.MovieSearchResult
import domain.repository.MovieRepository

internal class SearchMoviesUseCaseImpl(
    private val movieRepository: MovieRepository
) : SearchMoviesUseCase {
    override suspend fun execute(
        keyword: String,
        page: Int
    ): Result<MovieSearchResult> = resultOf {
        withContext(Dispatchers.IO) {
            movieRepository.searchMoviesByKeyword(keyword, page)
        }
    }
}

interface SearchMoviesUseCase {
    suspend fun execute(
        keyword: String,
        page: Int = 1
    ): Result<MovieSearchResult>
}
