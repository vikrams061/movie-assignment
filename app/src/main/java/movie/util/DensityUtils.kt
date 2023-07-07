package movie.util

import android.content.res.Resources

/**
 * Converts a float value to pixels based on the device's density.
 *
 * @return The converted value in pixels as an integer.
 */
fun Float.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
