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
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_another_activity.*
import kotlin.random.Random

class AnotherActivity : AppCompatActivity() {

    private var calculateBinder: Messenger? = null

    private val replyHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d(
                "Thread",
                "isMainThread:${Looper.myLooper()?.thread == Looper.getMainLooper().thread}"
            )
            output.text = msg.data.getString("calculationResult")
        }
    }

    private val replyMessenger = Messenger(replyHandler)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            calculateBinder = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            calculateBinder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_another_activity)
        handlerThread.start()
    }

    override fun onStart() {
        super.onStart()
        bindToService()

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
        calculateBinder?.send(Message()
            .apply {
                replyTo = replyMessenger
                data.putLong("first",first)
                data.putLong("second",second)
                data.putString("operation",operationSelector.selectedItem as? String)
            })
    }
}