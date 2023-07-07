package domain.repository

import common.model.Movie
import common.model.MovieSearchResult

interface MovieRepository {
    suspend fun searchMoviesByKeyword(keyword: String, page: Int): MovieSearchResult
    suspend fun searchMovieByImdbId(imdbId: String): Movie
}
