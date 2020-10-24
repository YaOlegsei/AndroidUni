package com.alexeykolotilo.myuniapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_another_activity.*

class AnotherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_another_activity)
    }

    override fun onStart() {
        super.onStart()
        textView.text = intent?.extras?.getString("myText")
    }
}