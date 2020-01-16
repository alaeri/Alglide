package com.alaeri.alglide.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alaeri.alglide.lib.Alglide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO replace with a listview when we can load an image
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity","${image.width}")
        val height = (Math.random() * 2000).toInt()
        Alglide.with(this).load(image, "https://placekitten.com/900/$height")
    }
}
