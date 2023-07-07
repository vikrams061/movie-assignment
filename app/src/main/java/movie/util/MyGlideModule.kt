package movie.util

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * Custom Glide module for registering components and configuring Glide options.
 */
@GlideModule
internal class MyGlideModule : AppGlideModule() {

    /**
     * Registers custom components and options for Glide.
     *
     * @param context The application context.
     * @param glide The Glide instance.
     * @param registry The registry for registering components.
     */

    override fun registerComponents(
        context: Context,
        glide: Glide,
        registry: Registry
    ) {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}
