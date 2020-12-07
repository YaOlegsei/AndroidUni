package com.alexeykolotilo.myuniapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_another_activity.*
import kotlin.random.Random

class AnotherActivity : AppCompatActivity() {

    private var calculateBinder: CalculateBinder? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            calculateBinder = service as? CalculateBinder
        }

        override fun onServiceDisconnected(name: ComponentName) {
            calculateBinder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_another_activity)
    }

    override fun onStart() {
        super.onStart()
        bindToService()
        handlerThread.start()

        textView.text = intent?.extras?.getString("myText")

        randomColorButton.setOnClickListener {
            val red = Random.nextInt(0, 256)
            val green = Random.nextInt(0, 256)
            val blue = Random.nextInt(0, 256)
            colorView.setBackgroundColor(Color.rgb(red, green, blue))
        }

        calculate.setOnClickListener {
            val firstValue =
                if (firstInput.text.isEmpty()) return@setOnClickListener
                else firstInput.text.toString().toLong()

            val secondValue =
                if (secondInput.text.isEmpty()) return@setOnClickListener
                else secondInput.text.toString().toLong()

            calculate(firstValue, secondValue)
        }
    }

    private fun bindToService() {
        Intent(
            this,
            CalculateService::class.java,
        ).apply {
            bindService(this, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quitSafely()
    }

    private val handlerThread = object : HandlerThread("MyThread") {
        override fun onLooperPrepared() {
            super.onLooperPrepared()
            backgroundHandler = Handler(looper)
        }
    }

    private lateinit var backgroundHandler: Handler

    private fun calculate(first: Long, second: Long) {
        val operation = operationSelector.selectedItem
        backgroundHandler.post {
            val result = when (operation) {
                "*" -> first * second
                "+" -> first + second
                "-" -> first - second
                "/" -> first / second
                else -> null
            }?.toString()

            Log.d("Thread", "isMainThread:${Looper.myLooper()?.thread == Looper.getMainLooper().thread}")

            output.handler.post {
                output.text = result
                Log.d("Thread", "isMainThread:${Looper.myLooper()?.thread == Looper.getMainLooper().thread}")
            }
        }
    }
}