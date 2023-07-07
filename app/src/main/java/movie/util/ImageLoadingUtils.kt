package movie.util

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * Loads an image from a Uri into this ImageView using Glide.
 *
 * @param uri The Uri or String Uri of the image to load.
 * @param cache Whether to enable caching for the image (default: true).
 * @param noFade Whether to disable image fading during loading (default: false).
 * @param noPlaceholder Whether to disable showing a placeholder image (default: false).
 * @param thumbnail The Uri of the thumbnail image to load (optional).
 * @param errorImage The Drawable to display in case of image loading error (optional).
 * @param onSuccess Callback to be invoked when the image is successfully loaded (optional).
 * @param onError Callback to be invoked when there's an error loading the image (optional).
 */
fun ImageView.loadUri(
    uri: Uri?,
    cache: Boolean = true,
    noFade: Boolean = false,
    noPlaceholder: Boolean = false,
    thumbnail: Uri? = null,
    errorImage: Drawable? = null,
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null
) {
    GlideApp.with(context)
        .load(uri)
        .thumbnail(GlideApp.with(context).load(thumbnail))
        .configure(
            true,
            cache,
            noFade,
            noPlaceholder,
            errorImage,
            onSuccess,
            onError
        )
        .into(this)
}

fun ImageView.loadUri(
    uri: String?,
    cache: Boolean = true,
    noFade: Boolean = false,
    noPlaceholder: Boolean = false,
    thumbnail: String? = null,
    errorImage: Drawable? = null,
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null
) = loadUri(
    uri?.let(Uri::parse),
    cache,
    noFade,
    noPlaceholder,
    thumbnail?.let(Uri::parse),
    errorImage,
    onSuccess,
    onError
)

fun GlideRequest<Drawable>.configure(
    centerCrop: Boolean = false,
    cache: Boolean = true,
    noFade: Boolean = false,
    noPlaceholder: Boolean = false,
    errorImage: Drawable? = null,
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null
) = this.diskCacheStrategy(if (cache) DiskCacheStrategy.AUTOMATIC else DiskCacheStrategy.NONE)
    .skipMemoryCache(!cache)
    .let { req ->
        errorImage?.let { req.error(it) } ?: req
    }
    .let {
        if (centerCrop) it.centerCrop() else it
    }
    .let {
        if (!noPlaceholder) it.placeholder(android.R.color.darker_gray) else it
    }
    .let {
        if (!noFade) it.transition(DrawableTransitionOptions().crossFade()) else it
    }
    .listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onError?.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onSuccess?.invoke()
            return false
        }
    })
