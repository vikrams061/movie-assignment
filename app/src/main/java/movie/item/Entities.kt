package movie.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableMovie(
    val imdbId: String,
    val posterUrl: String?
): Parcelable
