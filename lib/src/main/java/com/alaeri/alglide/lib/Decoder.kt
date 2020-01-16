package com.alaeri.alglide.lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Decoder(appContext: Context){
    fun decode(byteArray: ByteArray, width: Int, height: Int) : Bitmap {
        val discoverSizeOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, discoverSizeOptions)
        val targetSampleSize = calculateInSampleSize(discoverSizeOptions, width, height)
        val resizeOptions = BitmapFactory.Options().apply {
            inSampleSize = targetSampleSize
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, resizeOptions)
    }

    //https://developer.android.com/topic/performance/graphics/load-bitmap.html#load-bitmap
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

}