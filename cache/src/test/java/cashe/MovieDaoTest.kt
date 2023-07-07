package cashe

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import cache.room.MovieCacheModel
import cache.room.MovieDao
import cache.room.OmdbDatabase
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
Test class for MovieDao, which provides unit tests for database operations.
This class is annotated with @RunWith(AndroidJUnit4::class) to indicate that it is an Android JUnit test.
It tests the functionality of the MovieDao interface and its implementation.
*/
@RunWith(AndroidJUnit4::class)
open class MovieDaoTest {
    private lateinit var db: OmdbDatabase
    private lateinit var movieDao: MovieDao
    /**
    Setup method executed before each test case.
    It initializes an in-memory database and obtains the MovieDao instance.
     */
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            OmdbDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        movieDao = db.movieDao()
    }
    /**
    Teardown method executed after each test case.
    It clears all tables in the database and closes the database.
     */
    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.run {
            clearAllTables()
            close()
        }
    }
    /**
    Test case to verify that calling getMovieByImdbId with a non-existing IMDb ID returns null.
     */
    @Test
    fun shouldReturnNothing() {
        runBlocking {
            assertThat(movieDao.getMovieByImdbId("tt0372784")).isNull()
        }
    }
    /**
    Test case to verify that inserting movies into the database is successful.
     */
    @Test
    fun shouldInsertSuccessfully() {
        runBlocking {
            val testMovie1 = MovieCacheModel(
                "tt0372784",
                "Batman Begins",
                "2005",
                "movie"
            )
            val testMovie2 = MovieCacheModel(
                "tt0372785",
                "Batman Ends",
                "2007",
                "sequel"
            )
            movieDao.insertOrUpdate(
                testMovie1,
                testMovie2
            )
            assertThat(movieDao.getMovieByImdbId("tt0372784")).isEqualTo(testMovie1)
            assertThat(movieDao.getMovieByImdbId("tt0372785")).isEqualTo(testMovie2)
        }
    }
    /**
    Test case to verify that updating a movie in the database is successful.
     */

    @Test
    fun shouldUpdateSuccessfully() {
        runBlocking {
            val testMovie = MovieCacheModel(
                "tt0372784",
                "Batman Begins",
                "2005",
                "movie"
            )
            val updatedMovie = MovieCacheModel(
                "tt0372784",
                "Batman Begins",
                "2005",
                "movie"
            )
            movieDao.insertOrUpdate(
                testMovie
            )
            assertThat(movieDao.getMovieByImdbId("tt0372784")).isEqualTo(testMovie)
            movieDao.insertOrUpdate(updatedMovie)
            assertThat(movieDao.getMovieByImdbId("tt0372784")).isEqualTo(updatedMovie)
        }
    }
}
