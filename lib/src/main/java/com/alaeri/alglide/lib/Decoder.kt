package com.alaeri.alglide.lib

import android.content.Context
import android.graphics.Bitmap

class Decoder(appContext: Context){
    fun decode(compressedImageByteArray: ByteArray, width: Int, height: Int) : Bitmap {
        return Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
    }
}