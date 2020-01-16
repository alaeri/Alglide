package com.alaeri.alglide.lib

import android.view.View
import android.widget.ImageView
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Emmanuel Requier on 16/01/2020.
 *
 */
class ImageLoader(
    private val fetcher: Fetcher,
    private val decoder: Decoder,
    private val lifecycleScope: CoroutineScope,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main){


    private val runningJobs = ConcurrentHashMap<ImageView, Job>()

    fun load(imageView: ImageView, url: String){
        val jobToCancel = runningJobs[imageView]
        jobToCancel?.cancel()
        val job = lifecycleScope.launch {
            val bitmap = withContext(ioDispatcher){
                val compressedImageByteArray = fetcher.fetch(url)
                decoder.decode(compressedImageByteArray, imageView.width, imageView.height)
            }
            withContext(mainDispatcher){
                imageView.setImageBitmap(bitmap)
            }
            runningJobs.remove(imageView)
        }
        //https://github.com/coil-kt/coil/blob/master/coil-base/src/main/java/coil/memory/ViewTargetRequestManager.kt
        imageView.addOnAttachStateChangeListener(OnAttachStateChangeListener(imageView))
        runningJobs[imageView] = job
    }

    inner class OnAttachStateChangeListener(private val imageView: ImageView) :
        View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(p0: View?) {
                cancel(imageView)
            }

            override fun onViewAttachedToWindow(p0: View?) {}

        }

    internal fun cancel(imageView: ImageView){
        val jobToCancel = runningJobs[imageView]
        jobToCancel?.cancel()
    }
}