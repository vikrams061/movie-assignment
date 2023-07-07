package cache

import androidx.room.Room
import cache.room.OmdbDatabase
import data.source.MovieCacheDataSource
import org.koin.dsl.module

/**
Koin module for providing dependencies related to caching movie data.
This module provides the necessary dependencies for caching movie data, including the
OmdbDatabase, MovieCacheDataSource, and MovieDao.
*/
val cacheModule = module {
/*
Provides a single instance of OmdbDatabase using Room's database builder.
The database is built with the provided parameters and named "omdb.db".
The fallbackToDestructiveMigration() method is called to clear the database on migration,
as it is considered just a cache.
*/
    single {
        Room.databaseBuilder(
            get(),
            OmdbDatabase::class.java,
            "omdb.db"
        )
            .fallbackToDestructiveMigration() //Clear the db on migration - it's just a cache
            .build()
    }
    /**
    Provides a factory instance of MovieCacheDataSource by initializing MovieCacheDataSourceImpl
    with the OmdbDatabase dependency obtained from Koin.
     */
    factory<MovieCacheDataSource> {
        MovieCacheDataSourceImpl(get())
    }

    /**
    Provides a factory instance of MovieDao by retrieving the OmdbDatabase dependency
    and returning its movieDao() function to access the movie data in the database.
     */
    factory {
        get<OmdbDatabase>().movieDao()
    }
}
