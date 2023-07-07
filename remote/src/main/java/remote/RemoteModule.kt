package remote

import com.squareup.moshi.Moshi
import common.TAG_BOOLEAN_DEBUG
import common.TAG_INTERCEPTOR_API_KEY
import common.TAG_INTERCEPTOR_LOGGING
import common.TAG_OMDB_API_KEY
import data.source.MovieRemoteDataSource
import remote.adapter.MovieAdapter
import remote.adapter.MovieSearchAdapter
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteModule = module {

    single {
        RemoteFactory.buildOkHttpClient(
            listOf(get(named(TAG_INTERCEPTOR_API_KEY))),
            listOf(get(named(TAG_INTERCEPTOR_LOGGING)))
        )
    }

    single {
        val movieAdapter = MovieAdapter()
        Moshi.Builder()
            .add(movieAdapter)
            .add(MovieSearchAdapter(movieAdapter))
            .build()
    }

    factory<MovieRemoteDataSource> {
        MovieRemoteDataSourceImpl(get())
    }

    factory {
        RemoteFactory.buildRestApi(
            "http://www.omdbapi.com/",
            OmdbRestApi::class.java,
            get(),
            get()
        )
    }

    single {
        MoshiConverterFactory.create(get())
    }

    factory<Interceptor>(named(TAG_INTERCEPTOR_LOGGING)) {
        val isDebug: Boolean = get(named(TAG_BOOLEAN_DEBUG))
        HttpLoggingInterceptor().apply {
            level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    factory<Interceptor>(named(TAG_INTERCEPTOR_API_KEY)) {
        ApiKeyInterceptor(get(named(TAG_OMDB_API_KEY)))
    }
}
