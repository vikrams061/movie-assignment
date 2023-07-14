package cashe

import cache.MovieCacheDataSourceImpl
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import cache.room.MovieCacheModel
import cache.room.MovieDao
import data.model.MovieDataModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
Test class for MovieCacheDataSourceImpl, which provides unit tests for the caching data source implementation.
This class uses MockK library for mocking dependencies and verifies the behavior of MovieCacheDataSourceImpl.
 */
class MovieCacheDataSourceImplTest {
    private val dao: MovieDao = mockk()
    private val dataSource = MovieCacheDataSourceImpl(dao)
    /**
    Test case to verify that the desired movie is returned if it exists in the cache.
     */
    @Test
    fun `should return desired item if it exists`() {
        coEvery {
            dao.getMovieByImdbId("dd650fb7")
        } returns MovieCacheModel(
            "dd650fb7",
            "Batman",
            "2005",
            "movie",
            recordedAt = 1430000000000
        )
        runBlocking {
            val result = dataSource.loadMovieByImdbId("dd650fb7")
            assertThat(result).isEqualTo(
                Pair(
                    MovieDataModel(
                        "Batman",
                        "2005",
                        "dd650fb7",
                        "movie"
                    ),
                    1430000000000
                )
            )
            coVerify(exactly = 1) {
                dao.getMovieByImdbId("dd650fb7")
            }
        }
    }
    /**
    Test case to verify that null is returned if the item is not cached.
     */

    @Test
    fun `should return null if item is not cached`() {
        coEvery {
            dao.getMovieByImdbId(any())
        } returns null

        runBlocking {
            val result = dataSource.loadMovieByImdbId("dd650fb7")
            assertThat(result).isNull()
            coVerify(exactly = 1) {
                dao.getMovieByImdbId("dd650fb7")
            }
        }
    }

    /**
    Test case to verify that the DAO's save function is called correctly.
     */
    @Test
    fun `should call DAO's save function`() {
        coEvery {
            dao.insertOrUpdate(*anyVararg())
        } just Runs

        runBlocking {
            dataSource.saveMovie(
                MovieDataModel(
                    "Batman",
                    "2005",
                    "dd650fb7",
                    "movie"
                )
            )
        }
        coVerify(exactly = 1) {
            dao.insertOrUpdate(
                *varargAny {
                    if (position == 0) {
                        it.title == "Batman"
                                && it.year == "2005"
                                && it.imdbId == "dd650fb7"
                                && it.type == "movie"
                    } else false
                }
            )
        }
    }
}
