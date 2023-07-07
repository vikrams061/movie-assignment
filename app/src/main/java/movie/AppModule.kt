package movie

import common.TAG_BOOLEAN_DEBUG
import common.TAG_OMDB_API_KEY
import movie.assignment.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module

// The appModule is created using the module function provided by Koin.
// Inside the appModule, two single instances are defined using the single function.
// The single function creates a single instance of the specified type
// which will be shared across the application.
val appModule = module {
    single(named(TAG_BOOLEAN_DEBUG)) {
        BuildConfig.DEBUG
    }
    single(named(TAG_OMDB_API_KEY)) {
        BuildConfig.OMDB_API_KEY
    }
}
