package remote

import com.squareup.moshi.Moshi
import remote.adapter.MovieAdapter
import remote.adapter.MovieSearchAdapter
import okhttp3.Interceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import retrofit2.converter.moshi.MoshiConverterFactory

/**
Base class for API unit tests.
 */
internal abstract class BaseApiTest {

    protected val mockServer: MockWebServer = MockWebServer()

    init {
        mockServer.start()
    }
    /**
    Gets a mocked instance of the REST API.
    @param restApiInterceptors List of interceptors for the REST API.
    @param restApiNetworkInterceptors List of network interceptors for the REST API.
    @return The mocked REST API instance.
     */
    protected inline fun <reified T : Any> getMockedRestApi(
        restApiInterceptors: List<Interceptor>? = null,
        restApiNetworkInterceptors: List<Interceptor>? = null
    ): T {
        val movieAdapter = MovieAdapter()
        return RemoteFactory.buildRestApi(
            mockServer.url("/").toString(),
            T::class.java,
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(movieAdapter)
                    .add(MovieSearchAdapter(movieAdapter))
                    .build()
            ),
            RemoteFactory.buildOkHttpClient(restApiInterceptors, restApiNetworkInterceptors)
        )
    }
    /**
    Tears down the mock server after each test.
     */
    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}
