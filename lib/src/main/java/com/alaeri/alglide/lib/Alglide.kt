package com.alaeri.alglide.lib

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.alaeri.alglide.lib.util.SingletonHolder
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap


/**
 * Created by Emmanuel Requier on 16/01/2020.
 */
object Alglide{

    init{
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
    private val imageLoaders = ConcurrentHashMap<LifecycleOwner, ImageLoader>()
    private val fetcher : Fetcher by lazy { Fetcher() }
    private val decoderSingletonHolder = SingletonHolder<Decoder, Context> {
        Decoder(it.applicationContext)
    }

    fun with(activity: Activity): ImageLoader{
        val lifecycleOwner = activity as LifecycleOwner
        return getImageLoaderInstance(activity, lifecycleOwner)
    }

    fun with(fragment: Fragment): ImageLoader{
        val lifecycleOwner = fragment as LifecycleOwner
        val activity = fragment.activity ?: throw IllegalArgumentException("fragment $fragment must be attached to activity")
        return getImageLoaderInstance(activity, lifecycleOwner)
    }

    private fun getImageLoaderInstance(
        activity: Activity,
        lifecycleOwner: LifecycleOwner
    ): ImageLoader {
        val decoder = decoderSingletonHolder.getInstance(activity)
        return imageLoaders.getOrPut(lifecycleOwner) {
            ImageLoader(fetcher, decoder, lifecycleOwner.lifecycleScope)
        }
    }

}