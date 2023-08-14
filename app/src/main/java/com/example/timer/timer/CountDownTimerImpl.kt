package com.example.timer.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


private const val DELAY_MS = 1000L

class CountDownTimerImpl(private val scope: CoroutineScope) : CountDownTimer {

    private var jobTimer: Job? = null

    override fun start(startTimerInSeconds: Int, onTimerEnded: () -> Unit, onTick: (Int) -> Unit) {

        if(startTimerInSeconds <= 0)
            return

        jobTimer = scope.launch {
            countdownFlow(startTimerInSeconds).collect {
                onTick(it)
            }
            onTimerEnded()
        }
    }

    override fun stop() {
        jobTimer?.cancel()
        jobTimer = null
    }

    private fun countdownFlow(time: Int): Flow<Int> = flow {
        for (i in time - 1 downTo 0) {
            emit(i)
            delay(DELAY_MS)
        }
    }
}