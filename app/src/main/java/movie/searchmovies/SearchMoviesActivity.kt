package movie.searchmovies

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import movie.searchmovies.SearchMoviesScreen
import presentation.SearchViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.component.getScopeId
import kotlin.math.log

/**
SearchMoviesActivity is an internal class that extends AppCompatActivity and represents
the activity for searching movies in the application.
This class is responsible for setting up the UI and interaction with the SearchViewModel.
It uses Jetpack Compose for UI rendering and observes the view state from the SearchViewModel.
 */
internal class SearchMoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MaterialTheme {
                val viewModel: SearchViewModel = koinViewModel()
                // Render the SearchMoviesScreen with the collected view state from the SearchViewModel
                SearchMoviesScreen(
                    viewState = viewModel.viewState.collectAsStateWithLifecycle().value,
                    onQueryChange = viewModel::searchMovies,
                    onLoadingNextPage = viewModel::loadNextPage,
                )
            }
        }
    }
}
