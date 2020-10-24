package com.alexeykolotilo.myuniapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_another_activity.*
import kotlin.random.Random

class AnotherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_another_activity)
    }

    override fun onStart() {
        super.onStart()
        textView.text = intent?.extras?.getString("myText")

        randomColorButton.setOnClickListener {
            val red = Random.nextInt(0, 256)
            val green = Random.nextInt(0, 256)
            val blue = Random.nextInt(0, 256)
            colorView.setBackgroundColor(Color.rgb(red, green, blue))
        }
    }
}