package data.source

import data.model.MovieDataModel


interface MovieCacheDataSource {
    /**
     * fetch a movie from Room database given its imdb id
     * @param imdbId
     */
    suspend fun loadMovieByImdbId(
        imdbId: String
    ) : Pair<MovieDataModel, Long>?

    /**
     * save a movie to Room database
     */
    suspend fun saveMovie(movie: MovieDataModel)
}
