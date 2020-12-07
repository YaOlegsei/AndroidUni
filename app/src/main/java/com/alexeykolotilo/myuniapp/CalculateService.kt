package com.alexeykolotilo.myuniapp

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import androidx.core.app.JobIntentService

class CalculateService : JobIntentService(), CalculateBinder {

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    override fun onHandleWork(intent: Intent) {
        TODO("Not yet implemented")
    }

    override fun handleOperation(operationType: OperationType, first: Long, second: Long) {
        TODO("Not yet implemented")
    }


    enum class OperationType {
        ADD,
        MULTIPLY,
        SUBTRACT,
        DIVIDE,
    }
}

interface CalculateBinder {
    fun handleOperation(operationType: CalculateService.OperationType, first: Long, second: Long)
}