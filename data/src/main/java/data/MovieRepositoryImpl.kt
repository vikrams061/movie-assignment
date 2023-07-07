package data

import common.model.Movie
import common.model.MovieRate
import common.model.MovieSearchResult
import common.model.MovieType
import data.model.MovieDataModel
import data.source.MovieCacheDataSource
import data.source.MovieRemoteDataSource
import domain.repository.MovieRepository

/**
Implementation of the MovieRepository interface that interacts with the remote and cache data sources.
This class provides methods to search movies by IMDb ID or keyword. It first tries to load the movie from the cache data source,
and if it is not found or expired, it fetches the movie from the remote data source and saves it to the cache.
It also provides a helper extension function to convert a MovieDataModel to a Movie entity.
 */
internal class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieCacheDataSource: MovieCacheDataSource
) : MovieRepository {
/**
Searches a movie by IMDb ID. It first tries to load the movie from the cache data source.
If the movie is not found or expired, it fetches the movie from the remote data source and saves it to the cache.
Returns the converted Movie entity.
 */
    override suspend fun searchMovieByImdbId(imdbId: String): Movie {
        val movieCacheModel = try {
            movieCacheDataSource.loadMovieByImdbId(imdbId)
        } catch (ignored: Throwable) {
            null
        }
        val movieDataModel = movieCacheModel?.let {
            if(System.currentTimeMillis() - it.second > 5 * 60 * 1000) {
                fetchMovieFromRemoteAndSaveToCache(imdbId)
            } else {
                it.first
            }
        } ?: fetchMovieFromRemoteAndSaveToCache(imdbId)

        return movieDataModel.toMovie()
    }
    /**
    Searches movies by keyword using the remote data source.
    Returns the converted MovieSearchResult entity.
     */
    override suspend fun searchMoviesByKeyword(
        keyword: String,
        page: Int
    ): MovieSearchResult {
        return movieRemoteDataSource.searchMoviesByKeyword(keyword, page)
            .let { result ->
                MovieSearchResult(
                    result.movies.map { it.toMovie() },
                    result.totalResults
                )
            }
    }
    /**
    Fetches the movie from the remote data source, saves it to the cache data source,
    and returns the fetched MovieDataModel.
     */
    private suspend fun fetchMovieFromRemoteAndSaveToCache(imdbId: String) : MovieDataModel {
        return movieRemoteDataSource.searchMoviesByImdbId(imdbId)
            .also {
                try {
                    movieCacheDataSource.saveMovie(it)
                } catch (ignored: Throwable) {
                    // Doesn't matter if something wrong happens when saving
                }
            }
    }
    /**
    Extension function to split a string into a list of strings using the delimiter ", ".
     */
    private fun String.splitToList() : List<String> {
        return this.split(", ")
    }
    /**
    Extension function to convert a MovieDataModel to a Movie entity.
     */
    private fun MovieDataModel.toMovie() : Movie {
        return Movie(
            title,
            year,
            imdbId,
            when(type) {
                "movie" -> MovieType.Movie
                "series" -> MovieType.Series
                else -> MovieType.Other
            },
            poster,
            rated?.let {
                try {
                    MovieRate.valueOf(it.uppercase().replace("-", "_"))
                } catch (ignored: Throwable) {
                    MovieRate.UNRATED
                }
            } ?: MovieRate.UNRATED,
            released,
            runtime,
            genres?.splitToList(),
            directors?.splitToList(),
            writers?.splitToList(),
            actors?.splitToList(),
            plot,
            languages?.splitToList(),
            country?.splitToList(),
            awards,
            metaScore,
            imdbRating,
            imdbVotes,
            boxOffice,
            dvdRelease,
            production,
        )
    }
}
