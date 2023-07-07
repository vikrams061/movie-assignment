package cache

import cache.room.MovieCacheModel
import cache.room.MovieDao
import data.model.MovieDataModel
import data.source.MovieCacheDataSource

/**
Implementation of the MovieCacheDataSource interface that interacts with the local movie cache.
This class provides methods to load and save movie data from/to the movie cache database.
It uses the provided MovieDao to access the database and perform the necessary operations.
*/
internal class MovieCacheDataSourceImpl(
    private val movieDao: MovieDao
) : MovieCacheDataSource {
/*
Retrieves the movie data and its recorded timestamp from the cache database for the given IMDb ID.
Returns a Pair containing the MovieDataModel and the recordedAt timestamp, or null if not found.
*/
    override suspend fun loadMovieByImdbId(imdbId: String): Pair<MovieDataModel, Long>? {
        return movieDao.getMovieByImdbId(imdbId)?.let {
            Pair(
                MovieDataModel(
                    it.title,
                    it.year,
                    it.imdbId,
                    it.type,
                    it.poster,
                    it.rated,
                    it.released,
                    it.runtime,
                    it.genres,
                    it.directors,
                    it.writers,
                    it.actors,
                    it.plot,
                    it.languages,
                    it.countries,
                    it.awards,
                    it.metaScore,
                    it.imdbRating,
                    it.imdbVotes,
                    it.boxOffice,
                    it.dvdRelease,
                    it.production,
                    it.website
                ),
                it.recordedAt
            )
        }
    }

/**
Saves the movie data to the cache database.
Inserts or updates the MovieCacheModel with the corresponding movie data fields.
 */
    override suspend fun saveMovie(movie: MovieDataModel) {
        movieDao.insertOrUpdate(
            MovieCacheModel(
                imdbId = movie.imdbId,
                title = movie.title,
                year = movie.year,
                type = movie.type,
                poster = movie.poster,
                rated = movie.rated,
                released = movie.released,
                runtime = movie.runtime,
                genres = movie.genres,
                directors = movie.directors,
                writers = movie.writers,
                actors = movie.actors,
                plot = movie.plot,
                languages = movie.languages,
                countries = movie.country,
                awards = movie.awards,
                metaScore = movie.metaScore,
                imdbRating = movie.imdbRating,
                imdbVotes = movie.imdbVotes,
                boxOffice = movie.boxOffice,
                dvdRelease = movie.dvdRelease,
                production = movie.production,
                website = movie.website
            )
        )
    }
}
