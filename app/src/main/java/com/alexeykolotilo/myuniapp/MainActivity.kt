package com.alexeykolotilo.myuniapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {
            Intent(this, AnotherActivity::class.java).also { intent ->
                intent.putExtras(
                    bundleOf("myText" to textInput.text.toString())
                )
                startActivity(intent, bundleOf())
            }
        }
    }
}